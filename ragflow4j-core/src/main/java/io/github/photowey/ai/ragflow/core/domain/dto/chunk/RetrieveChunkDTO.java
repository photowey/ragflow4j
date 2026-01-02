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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.photowey.ai.ragflow.core.domain.dto.MetadataDTO;
import io.github.photowey.ai.ragflow.core.domain.model.DocumentAggregation;
import io.github.photowey.ai.ragflow.core.domain.model.RetrieveChunk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RetrieveChunkDTO extends MetadataDTO {

    private static final long serialVersionUID = 352124429600249171L;

    @JsonProperty("chunks")
    private List<RetrieveChunk> chunks;
    @JsonProperty("doc_aggs")
    private List<DocumentAggregation> docAggs;
    @JsonProperty("total")
    private Integer total;

    // ----------------------------------------------------------------

    public List<RetrieveChunk> chunks() {
        return chunks;
    }

    public List<DocumentAggregation> docAggs() {
        return docAggs;
    }

    public Integer total() {
        return total;
    }
}
