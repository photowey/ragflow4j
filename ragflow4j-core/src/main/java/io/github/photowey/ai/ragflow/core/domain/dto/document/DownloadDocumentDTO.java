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
package io.github.photowey.ai.ragflow.core.domain.dto.document;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.github.photowey.ai.ragflow.core.domain.dto.MetadataDTO;
import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Download document.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#download-document">Download document</a>
 * @since 2025/11/30
 */
@Deprecated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("all")
public class DownloadDocumentDTO extends MetadataDTO implements Serializable {

    private static final long serialVersionUID = 1827453946696218990L;

    private Integer code;
    private String message;

    private String filename;
    private byte[] data;

    // ----------------------------------------------------------------

    public boolean determineIsOk() {
        if (Objects.isNull(this.code)) {
            return false;
        }

        return RAGFlowDictionary.ErrorCode.determineIsOk(this.code);
    }

}
