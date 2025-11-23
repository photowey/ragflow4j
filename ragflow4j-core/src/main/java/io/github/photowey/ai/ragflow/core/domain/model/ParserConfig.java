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
package io.github.photowey.ai.ragflow.core.domain.model;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code ParserConfig}.
 *
 * @author weichangjun
 * @version 2025.0.22.0.1
 * @since 2025/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParserConfig implements Serializable {

    private static final long serialVersionUID = 6802589331999497424L;

    /**
     * Defaults to 0
     * Minimum: 0
     * Maximum: 32
     */
    @Min(0)
    @Max(32)
    @JsonProperty("auto_keywords")
    private Integer autoKeywords;

    /**
     * Defaults to 0
     * Minimum: 0
     * Maximum: 10
     */
    @Min(0)
    @Max(10)
    @JsonProperty("auto_questions")
    private Integer autoQuestions;

    /**
     * Defaults to 512
     * Minimum: 1
     * Maximum: 2048
     */
    @Min(1)
    @Max(2048)
    @JsonProperty("chunk_token_num")
    private Integer chunkTokenNum;

    /**
     * Defaults to "\n".
     */
    @JsonProperty("delimiter")
    private String delimiter;

    /**
     * Indicates whether to convert Excel documents into HTML format.
     * Defaults to false
     */
    @JsonProperty("html4excel")
    private Boolean html4excel;

    /**
     * Defaults to DeepDOC.
     */
    @JsonProperty("layout_recognize")
    private String layoutRecognize;

    /**
     * Refer to Use tag set.
     * Must include a list of dataset IDs, where each dataset is parsed using the Tag Chunking Method
     */
    @JsonProperty("tag_kb_ids")
    private List<String> tagKbIds;

    /**
     * For PDF only.
     * Defaults to 12
     * Minimum: 1
     */
    @Min(1)
    @JsonProperty("task_page_size")
    private Integer taskPageSize;

    /**
     * RAPTOR-specific settings.
     * Defaults to: {"use_raptor": false}
     */
    @JsonProperty("raptor")
    private Raptor raptor;

    /**
     * GRAPHRAG-specific settings.
     * Defaults to: {"use_graphrag": false}
     */
    @JsonProperty("graphrag")
    private Graphrag graphrag;
}
