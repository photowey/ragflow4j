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
package io.github.photowey.ai.ragflow.core.domain.download;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents a handle to a downloaded document, providing access to its metadata and content.
 *
 * <p>
 * This interface is designed for **one-time consumption only**: either {@link #getInputStream()}
 * or {@link #writeTo(OutputStream)} may be called **once and only once** per instance.
 * Subsequent calls will result in undefined behavior (e.g., duplicate HTTP requests,
 * stream already consumed errors, or exceptions depending on the implementation).
 *
 * <p>
 * Implementations are typically backed by a reactive stream (e.g., from WebClient) and
 * avoid loading the entire content into memory unless explicitly requested via
 * {@link #readAllBytes()}.
 *
 * <p>
 * Although this interface extends {@link AutoCloseable}, the default {@link #close()}
 * implementation is a no-op. Resource cleanup is generally handled automatically during
 * stream consumption. However, if {@link #getInputStream()} is used, the returned
 * {@code InputStream} **must be closed** by the caller to ensure proper resource release.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/12/06
 */
public interface DownloadHandle extends AutoCloseable {

    /**
     * Returns metadata about the download, such as status code, message, and filename.
     *
     * <p>
     * The metadata is available immediately and does not require consuming the content stream.
     *
     * @return the download metadata; never {@code null}
     */
    DownloadMetadata getMetadata();

    /**
     * Returns an {@link InputStream} for reading the downloaded content.
     *
     * <p>
     * <strong>Important:</strong> This method may initiate the underlying data transfer
     * (e.g., trigger an HTTP request if not already done). It must be called at most once.
     * The returned stream is backed by a reactive data pipeline and may start a background
     * thread to bridge asynchronous data buffers into a blocking stream.
     *
     * <p>
     * The caller **must close** the returned {@code InputStream} to release resources.
     * Failure to do so may lead to resource leaks or hanging threads.
     * </p>
     *
     * @return a readable input stream containing the document content
     * @throws IOException           if an I/O error occurs while preparing the stream
     * @throws IllegalStateException if this handle has already been consumed
     */
    InputStream getInputStream() throws IOException;

    /**
     * Reads the entire content into a byte array.
     *
     * <p>
     * This is a convenience method that internally uses {@link #writeTo(OutputStream)}
     * with a {@link ByteArrayOutputStream}. It should only be used for small files,
     * as it loads the entire content into memory.
     *
     * @return the full content as a byte array
     * @throws IOException           if an I/O error occurs during reading
     * @throws IllegalStateException if this handle has already been consumed
     */
    default byte[] readAllBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.writeTo(baos);
        return baos.toByteArray();
    }

    /**
     * Writes the downloaded content directly to the given output stream.
     *
     * <p>
     * This is the most efficient way to process large files, as it avoids intermediate
     * buffering in memory. The method blocks until all data has been written or an error occurs.
     *
     * <p>
     * <strong>Note:</strong> The provided {@code OutputStream} is <em>not</em> closed by this method.
     * The caller is responsible for closing it.
     *
     * @param out the destination output stream; must not be {@code null}
     * @throws IOException           if an I/O error occurs during writing
     * @throws IllegalStateException if this handle has already been consumed
     */
    void writeTo(OutputStream out) throws IOException;

    /**
     * Closes this handle and releases any associated resources.
     *
     * <p>
     * The default implementation is a no-op, as actual resource management is tied to
     * the consumption of the content stream (via {@link #getInputStream()} or {@link #writeTo(OutputStream)}).
     * Subclasses may override this method if additional cleanup is required.
     *
     * @throws Exception if an error occurs during closing
     */
    @Override
    default void close() throws Exception {
        // No-op by design; resources are managed during stream consumption.
    }

    // ----------------------------------------------------------------

    default boolean determineIsOk() {
        return this.getMetadata().determineIsOk();
    }
}
