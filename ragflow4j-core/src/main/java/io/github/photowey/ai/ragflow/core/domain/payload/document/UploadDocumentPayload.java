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
package io.github.photowey.ai.ragflow.core.domain.payload.document;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import io.github.photowey.ai.ragflow.core.domain.payload.AbstractPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Upload documents.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#upload-documents">Upload documents</a>
 * @since 2025/11/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class UploadDocumentPayload extends AbstractPayload {

    private static final long serialVersionUID = -3650860345022157213L;

    @Valid
    @NotEmpty(message = "Documents must not be empty")
    private List<Document> documents;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(fluent = true)
    public static class Document implements Serializable {

        private static final long serialVersionUID = 3297280821128977767L;

        @NotBlank(message = "Document name must not be blank")
        private String documentName;

        @NotBlank(message = "Document original name must not be blank")
        private String originalName;
        @NotEmpty(message = "Document data must not be empty")
        private byte[] data;
    }
}
