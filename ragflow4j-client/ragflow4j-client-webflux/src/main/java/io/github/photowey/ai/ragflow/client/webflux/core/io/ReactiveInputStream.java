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
package io.github.photowey.ai.ragflow.client.webflux.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;

import reactor.core.publisher.Flux;

/**
 * {@code ReactiveInputStream}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/12/06
 */
public class ReactiveInputStream extends InputStream {

    private final Iterator<DataBuffer> buffers;
    private ByteBuffer currentBuffer;

    public ReactiveInputStream(Flux<DataBuffer> dataBufferFlux) {
        List<DataBuffer> bufferList = dataBufferFlux.collectList().block();
        this.buffers = Objects.nonNull(bufferList)
            ? bufferList.iterator()
            : Collections.emptyIterator();
    }

    @Override
    public int read() throws IOException {
        if (this.currentBuffer == null || !this.currentBuffer.hasRemaining()) {
            if (this.hasNoMoreBuffers()) {
                return -1;
            }
        }

        return this.currentBuffer.get() & 0xFF;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int totalRead = 0;
        while (len > 0) {
            if (this.currentBuffer == null || !this.currentBuffer.hasRemaining()) {
                if (this.hasNoMoreBuffers()) {
                    return totalRead > 0 ? totalRead : -1;
                }
            }

            int toRead = Math.min(this.currentBuffer.remaining(), len);
            this.currentBuffer.get(b, off, toRead);
            off += toRead;
            len -= toRead;
            totalRead += toRead;
        }

        return totalRead;
    }

    @Override
    public void close() throws IOException {
        while (this.buffers.hasNext()) {
            DataBuffer buffer = this.buffers.next();
            DataBufferUtils.release(buffer);
        }
    }

    private boolean hasNoMoreBuffers() {
        if (this.buffers.hasNext()) {
            DataBuffer next = this.buffers.next();
            this.currentBuffer = next.asByteBuffer();

            return false;
        }

        return true;
    }
}
