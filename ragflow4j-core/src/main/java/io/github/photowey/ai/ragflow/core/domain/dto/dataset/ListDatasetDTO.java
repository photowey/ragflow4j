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
package io.github.photowey.ai.ragflow.core.domain.dto.dataset;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * {@code ListDatasetDTO}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListDatasetDTO extends MetadataDTO implements Serializable {

    private static final long serialVersionUID = 1618306494732628562L;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("chunk_count")
    private Integer chunkCount;

    @JsonProperty("chunk_method")
    private String chunkMethod;

    @JsonProperty("create_date")
    private String createDate;

    @JsonProperty("create_time")
    private Long createTime;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("description")
    private String description;

    @JsonProperty("document_count")
    private Integer documentCount;

    @JsonProperty("embedding_model")
    private String embeddingModel;

    @JsonProperty("id")
    private String id;

    @JsonProperty("language")
    private String language;

    @JsonProperty("name")
    private String name;

    @JsonProperty("pagerank")
    private Integer pagerank;

    @JsonProperty("parser_config")
    private ParserConfig parserConfig;

    @JsonProperty("permission")
    private String permission;

    @JsonProperty("similarity_threshold")
    private BigDecimal similarityThreshold;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tenant_id")
    private String tenantId;

    @JsonProperty("token_num")
    private Integer tokenNum;

    @JsonProperty("update_date")
    private String updateDate;

    @JsonProperty("update_time")
    private Long updateTime;

    @JsonProperty("vector_similarity_weight")
    private BigDecimal vectorSimilarityWeight;

    // ----------------------------------------------------------------

    public String avatar() {
        return avatar;
    }

    public Integer chunkCount() {
        return chunkCount;
    }

    public String chunkMethod() {
        return chunkMethod;
    }

    public String createDate() {
        return createDate;
    }

    public Long createTime() {
        return createTime;
    }

    public String createdBy() {
        return createdBy;
    }

    public String description() {
        return description;
    }

    public Integer documentCount() {
        return documentCount;
    }

    public String embeddingModel() {
        return embeddingModel;
    }

    public String id() {
        return id;
    }

    public String language() {
        return language;
    }

    public String name() {
        return name;
    }

    public Integer pagerank() {
        return pagerank;
    }

    public ParserConfig parserConfig() {
        return parserConfig;
    }

    public String permission() {
        return permission;
    }

    public BigDecimal similarityThreshold() {
        return similarityThreshold;
    }

    public String status() {
        return status;
    }

    public String tenantId() {
        return tenantId;
    }

    public Integer tokenNum() {
        return tokenNum;
    }

    public String updateDate() {
        return updateDate;
    }

    public Long updateTime() {
        return updateTime;
    }

    public BigDecimal vectorSimilarityWeight() {
        return vectorSimilarityWeight;
    }
}
