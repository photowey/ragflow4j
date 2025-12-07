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

import io.github.photowey.ai.ragflow.core.domain.download.DownloadHandle;
import io.github.photowey.ai.ragflow.core.domain.download.DownloadMetadata;

/**
 * {@code ErrorDownloadHandle}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/12/06
 */
public class ErrorDownloadHandle implements DownloadHandle {

    private final DownloadMetadata metadata;

    public ErrorDownloadHandle(DownloadMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public DownloadMetadata getMetadata() {
        return metadata;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        throw new IOException("Download failed: " + metadata.message());
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        throw new IOException("Download failed: " + metadata.message());
    }

    @Override
    public void close() {
        // nothing
    }
}
