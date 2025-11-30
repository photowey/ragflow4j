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
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get knowledge graph.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#get-knowledge-graph">Get knowledge graph</a>
 * @since 2025/11/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeGraphDTO implements Serializable {

    private static final long serialVersionUID = -2675243353079780187L;

    @JsonProperty("graph")
    private Graph graph;

    @JsonProperty("mind_map")
    private Map<String, Object> mindMap;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Graph implements Serializable {

        private static final long serialVersionUID = -8937899857862430543L;

        @JsonProperty("directed")
        private Boolean directed;

        @JsonProperty("edges")
        private List<Edge> edges;

        @JsonProperty("graph")
        private GraphMeta graph;

        @JsonProperty("multigraph")
        private Boolean multigraph;

        @JsonProperty("nodes")
        private List<Node> nodes;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Edge implements Serializable {

        private static final long serialVersionUID = 6606206490332224226L;

        @JsonProperty("description")
        private String description;

        @JsonProperty("keywords")
        private List<String> keywords;

        @JsonProperty("source")
        private String source;

        @JsonProperty("source_id")
        private List<String> sourceIds;

        @JsonProperty("src_id")
        private String sourceId;

        @JsonProperty("target")
        private String target;

        @JsonProperty("tgt_id")
        private String targetId;

        @JsonProperty("weight")
        private BigDecimal weight;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GraphMeta implements Serializable {

        private static final long serialVersionUID = 4614517741754649225L;

        @JsonProperty("source_id")
        private List<String> sourceIds;
    }

    @Data
    @Builder
    public static class Node implements Serializable {

        private static final long serialVersionUID = -2402787841205594623L;

        @JsonProperty("description")
        private String description;

        @JsonProperty("entity_name")
        private String entityName;

        @JsonProperty("entity_type")
        private String entityType;

        @JsonProperty("id")
        private String id;

        @JsonProperty("pagerank")
        private BigDecimal pagerank;

        @JsonProperty("rank")
        private Integer rank;

        @JsonProperty("source_id")
        private List<String> sourceIds;
    }
}
