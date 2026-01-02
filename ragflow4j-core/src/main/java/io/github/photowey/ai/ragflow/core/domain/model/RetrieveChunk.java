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
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class RetrieveChunk implements Serializable {

    private static final long serialVersionUID = -6268003416829099463L;

    @JsonProperty("content")
    private String content;

    @JsonProperty("content_ltks")
    private String contentLtks;

    @JsonProperty("document_id")
    private String documentId;

    @JsonProperty("document_keyword")
    private String documentKeyword;

    @JsonProperty("highlight")
    private String highlight;

    @JsonProperty("id")
    private String id;

    @JsonProperty("image_id")
    private String imageId;

    @JsonProperty("important_keywords")
    private List<String> importantKeywords;

    @JsonAlias(value = {"kb_id", "dataset_id"})
    @JsonProperty("dataset_id")
    private String datasetId;

    @JsonProperty("positions")
    private List<List<Integer>> positions;

    @JsonProperty("similarity")
    private BigDecimal similarity;

    @JsonProperty("term_similarity")
    private BigDecimal termSimilarity;

    @JsonProperty("vector_similarity")
    private BigDecimal vectorSimilarity;

    @JsonProperty("extensions")
    private List<Map<String, Object>> extensions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SimpleChunk implements Serializable {

        private static final long serialVersionUID = 5207540576257092942L;

        private String id;

        // ----------------------------------------------------------------

        private String content;

        // ----------------------------------------------------------------

        private String datasetId;
        private String documentId;
        private String documentName;

        // ----------------------------------------------------------------

        private String imageId;

        // ----------------------------------------------------------------

        private BigDecimal similarity;
        private BigDecimal termSimilarity;
        private BigDecimal vectorSimilarity;

        private List<Map<String, Object>> extensions;

        // ----------------------------------------------------------------

        public String id() {
            return id;
        }

        public String content() {
            return content;
        }

        public String datasetId() {
            return datasetId;
        }

        public String documentId() {
            return documentId;
        }

        public String documentName() {
            return documentName;
        }

        public String imageId() {
            return imageId;
        }

        public BigDecimal similarity() {
            return similarity;
        }

        public BigDecimal termSimilarity() {
            return termSimilarity;
        }

        public BigDecimal vectorSimilarity() {
            return vectorSimilarity;
        }

        public List<Map<String, Object>> extensions() {
            return extensions;
        }
    }

    // ----------------------------------------------------------------

    public SimpleChunk toSimpleChunk() {
        return SimpleChunk.builder()
            .id(this.id)
            .content(this.content)
            .datasetId(this.datasetId)
            .documentId(this.documentId)
            .documentName(this.documentKeyword)
            .imageId(this.imageId)
            .similarity(this.similarity)
            .termSimilarity(this.termSimilarity)
            .vectorSimilarity(this.vectorSimilarity)
            .extensions(this.extensions)
            .build();
    }

    // ----------------------------------------------------------------


    public String content() {
        return content;
    }

    public String contentLtks() {
        return contentLtks;
    }

    public String documentId() {
        return documentId;
    }

    public String documentKeyword() {
        return documentKeyword;
    }

    public String highlight() {
        return highlight;
    }

    public String id() {
        return id;
    }

    public String imageId() {
        return imageId;
    }

    public List<String> importantKeywords() {
        return importantKeywords;
    }

    public String datasetId() {
        return datasetId;
    }

    public List<List<Integer>> positions() {
        return positions;
    }

    public BigDecimal similarity() {
        return similarity;
    }

    public BigDecimal termSimilarity() {
        return termSimilarity;
    }

    public BigDecimal vectorSimilarity() {
        return vectorSimilarity;
    }

    public List<Map<String, Object>> extensions() {
        return extensions;
    }
}
