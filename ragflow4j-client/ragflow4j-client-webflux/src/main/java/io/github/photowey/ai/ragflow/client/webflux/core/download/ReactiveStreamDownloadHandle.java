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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;

import io.github.photowey.ai.ragflow.client.webflux.core.io.ReactiveInputStream;
import io.github.photowey.ai.ragflow.core.domain.download.DownloadHandle;
import io.github.photowey.ai.ragflow.core.domain.download.DownloadMetadata;

import reactor.core.publisher.Flux;

/**
 * {@code ReactiveStreamDownloadHandle}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/12/06
 */
public class ReactiveStreamDownloadHandle implements DownloadHandle {

    private final DownloadMetadata metadata;
    private final Flux<DataBuffer> stream;
    private final AtomicBoolean consumed = new AtomicBoolean(false);

    public ReactiveStreamDownloadHandle(DownloadMetadata metadata, Flux<DataBuffer> stream) {
        this.metadata = metadata;
        this.stream = stream;
    }

    @Override
    public DownloadMetadata getMetadata() {
        return metadata;
    }

    @Override
    public InputStream getInputStream() {
        this.checkConsumed();

        // return ReactiveStreamBridge.toInputStream(stream)
        return new ReactiveInputStream(this.stream);
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        this.checkConsumed();

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> errorRef = new AtomicReference<>();

        DataBufferUtils.write(this.stream, out)
            .doOnNext(DataBufferUtils.releaseConsumer())
            .doOnError(errorRef::set)
            .doFinally(signal -> {
                latch.countDown();
            })
            .subscribe();

        tryAwait(latch);
        checkThrowable(errorRef);
    }

    @Override
    public void close() {
        if (this.consumed.compareAndSet(false, true)) {
            this.stream.subscribe(
                DataBufferUtils.releaseConsumer(),
                error -> {
                    // nothing.
                }
            );
        }
    }

    // ----------------------------------------------------------------

    private void checkConsumed() {
        if (!this.consumed.compareAndSet(false, true)) {
            throw new IllegalStateException("DownloadHandle has already been consumed");
        }
    }

    private static void tryAwait(CountDownLatch latch) throws IOException {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while writing stream", e);
        }
    }

    private static void checkThrowable(AtomicReference<Throwable> errorRef) throws IOException {
        if (Objects.nonNull(errorRef.get())) {
            throw new IOException("Write failed", errorRef.get());
        }
    }
}
