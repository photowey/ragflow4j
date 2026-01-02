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
package io.github.photowey.ai.ragflow.core.domain.payload.chunk;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.photowey.ai.ragflow.core.domain.model.MetadataCondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Retrieve chunks.
 *
 * @author photowey
 * @version 2025.0.22.1.1
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#retrieve-chunks">Retrieve chunks</a>
 * @since 2026/01/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetrieveChunkPayload implements Serializable {

    private static final long serialVersionUID = -5004869873689206893L;

    @JsonProperty("question")
    private String question;

    @JsonProperty("dataset_ids")
    private List<String> datasetIds;

    @JsonProperty("document_ids")
    private List<String> documentIds;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("page_size")
    private Integer pageSize;

    @JsonProperty("similarity_threshold")
    private BigDecimal similarityThreshold;

    @JsonProperty("vector_similarity_weight")
    private BigDecimal vectorSimilarityWeight;

    @JsonProperty("top_k")
    private Integer topK;

    @JsonProperty("rerank_id")
    private String rerankId;

    @JsonProperty("keyword")
    private Boolean keyword;

    @JsonProperty("highlight")
    private Boolean highlight;

    @JsonProperty("cross_languages")
    private List<String> crossLanguages;

    @JsonProperty("metadata_condition")
    private MetadataCondition metadataCondition;

    @JsonProperty("use_kg")
    private Boolean useKg;
}
