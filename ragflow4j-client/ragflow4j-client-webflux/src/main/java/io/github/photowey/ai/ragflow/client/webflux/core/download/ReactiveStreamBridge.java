/*
 * Copyright (c) 2025-present The Ragflow4j Authors. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.photowey.ai.ragflow.client.webflux.core.download;

import java.io.Closeable;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;

import io.github.photowey.ai.ragflow.core.util.AssertionErrors;

import reactor.core.publisher.Flux;

/**
 * {@code ReactiveStreamBridge}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/12/06
 */
public final class ReactiveStreamBridge {

    private ReactiveStreamBridge() {
        AssertionErrors.throwz(ReactiveStreamBridge.class);
    }

    private static final int DEFAULT_PIPE_SIZE = 8192;
    private static final long DEFAULT_CLOSE_TIMEOUT_SECONDS = 10;

    public static InputStream toInputStream(Flux<DataBuffer> flux) {
        return toInputStream(flux, DEFAULT_PIPE_SIZE, DEFAULT_CLOSE_TIMEOUT_SECONDS);
    }

    public static InputStream toInputStream(Flux<DataBuffer> flux, int pipeSize, long closeTimeoutSeconds) {
        PipedInputStream pipedIn = new PipedInputStream(pipeSize);
        PipedOutputStream pipedOut = createPipedOutputStream(pipedIn);

        @SuppressWarnings("all")
        ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
            Thread t = new Thread(runnable, "reactive-to-inputstream-bridge");
            t.setDaemon(true);
            return t;
        });

        AtomicReference<Throwable> errorRef = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        executor.execute(() -> {
            flux.doOnNext(DataBufferUtils::release)
                .subscribe(
                    buffer -> {
                        try {
                            ByteBuffer bb = buffer.asByteBuffer();
                            byte[] bytes = new byte[bb.remaining()];
                            bb.get(bytes);
                            pipedOut.write(bytes);
                        } catch (IOException e) {
                            errorRef.set(e);
                            latch.countDown();
                        }
                    },
                    error -> {
                        errorRef.set(error);
                        latch.countDown();
                    },
                    () -> {
                        safeClose(pipedOut);
                        latch.countDown();
                    }
                );
        });

        return new FilterInputStream(pipedIn) {
            @Override
            public int read() throws IOException {
                this.checkError();
                return super.read();
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                this.checkError();
                return super.read(b, off, len);
            }

            private void checkError() throws IOException {
                if (errorRef.get() != null) {
                    throw new IOException("Reactive stream failed", errorRef.get());
                }
            }

            @Override
            public void close() throws IOException {
                try {
                    super.close();
                } finally {
                    safeClose(pipedOut);
                    executor.shutdownNow();
                    try {
                        if (!latch.await(closeTimeoutSeconds, TimeUnit.SECONDS)) {
                            // timeout, but we've done our best
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };
    }

    private static PipedOutputStream createPipedOutputStream(PipedInputStream pipedIn) {
        try {
            return new PipedOutputStream(pipedIn);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to create pipe", e);
        }
    }

    private static void safeClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
                // ignore
            }
        }
    }
}
