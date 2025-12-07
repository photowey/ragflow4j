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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.photowey.ai.ragflow.core.domain.dto.MetadataDTO;
import io.github.photowey.ai.ragflow.core.domain.model.ParserConfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadDocumentDTO extends MetadataDTO implements Serializable {

    private static final long serialVersionUID = -3718360920182426815L;

    @JsonProperty("chunk_method")
    private String chunkMethod;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("dataset_id")
    private String datasetId;

    @JsonProperty("id")
    private String id;

    @JsonProperty("location")
    private String location;

    @JsonProperty("name")
    private String name;

    @JsonProperty("parser_config")
    private ParserConfig parserConfig;

    @JsonProperty("run")
    private String run;

    @JsonProperty("size")
    private Long size;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("type")
    private String type;
}


