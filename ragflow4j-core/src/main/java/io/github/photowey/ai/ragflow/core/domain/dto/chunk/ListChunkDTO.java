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
package io.github.photowey.ai.ragflow.core.domain.dto.chunk;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
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
 * List chunks.
 *
 * @author photowey
 * @version 2025.0.22.1.1
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#list-chunks">List chunks</a>
 * @since 2026/01/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListChunkDTO extends MetadataDTO {

    private static final long serialVersionUID = 3879789853118730524L;

    @JsonProperty("chunks")
    private List<ListChunk> chunks;

    @JsonProperty("doc")
    private Document document;

    @JsonProperty("total")
    private Integer total;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListChunk implements Serializable {

        private static final long serialVersionUID = -1259222491060479374L;

        @JsonProperty("available")
        private Boolean available;
        @JsonProperty("available_int")
        private Integer availableInt;

        @JsonProperty("content")
        private String content;

        @JsonProperty("document_id")
        private String documentId;

        @JsonProperty("docnm_kwd")
        private String documentKeyword;

        @JsonProperty("dataset_id")
        private String datasetId;

        @JsonProperty("id")
        private String id;

        @JsonProperty("image_id")
        private String imageId;

        @JsonProperty("important_keywords")
        private List<String> importantKeywords;

        // ----------------------------------------------------------------

        @JsonProperty("positions")
        private List<List<Integer>> positions;
        @JsonProperty("questions")
        private List<String> questions;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Document implements Serializable {

        private static final long serialVersionUID = 2340572464600008949L;

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

        @JsonProperty("dataset_id")
        private String datasetId;

        @JsonProperty("id")
        private String id;

        @JsonProperty("location")
        private String location;

        // ----------------------------------------------------------------

        @JsonProperty("meta_fields")
        private Map<String, Object> metadata;

        // ----------------------------------------------------------------

        @JsonProperty("name")
        private String name;

        @JsonProperty("parser_config")
        private ParserConfig parserConfig;

        @JsonProperty("process_begin_at")
        private String processBeginAt;

        @JsonProperty("process_duration")
        @JsonAlias(value = {"process_duration", "process_duation"})
        private BigDecimal processDuration;

        @JsonProperty("progress")
        private BigDecimal progress;

        @JsonProperty("progress_msg")
        private String progressMessage;

        @JsonProperty("run")
        private String run;

        @JsonProperty("size")
        private Long size;

        @JsonProperty("source_type")
        private String sourceType;

        @JsonProperty("status")
        private String status;

        @JsonProperty("thumbnail")
        private String thumbnail;

        @JsonProperty("token_count")
        private Integer tokenCount;

        @JsonProperty("type")
        private String type;

        @JsonProperty("update_date")
        private String updateDate;

        @JsonProperty("update_time")
        private Long updateTime;
    }

    public List<ListChunk> chunks() {
        return chunks;
    }

    public Document document() {
        return document;
    }

    public Integer total() {
        return total;
    }
}
