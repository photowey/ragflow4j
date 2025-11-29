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
import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.photowey.ai.ragflow.core.domain.model.ParserConfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Document.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentDTO implements Serializable {

    private static final long serialVersionUID = -6802797247729432817L;

    @JsonProperty("chunk_count")
    private Integer chunkCount;

    @JsonProperty("create_date")
    private String createDate;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("id")
    private String id;

    @JsonProperty("knowledgebase_id")
    private String knowledgebaseId;

    @JsonProperty("location")
    private String location;

    @JsonProperty("name")
    private String name;

    @JsonProperty("parser_config")
    private ParserConfig parserConfig;

    @JsonProperty("chunk_method")
    private String chunkMethod;

    @JsonProperty("process_begin_at")
    private String processBeginAt;

    @JsonProperty("process_duration")
    private BigDecimal processDuration;

    @JsonProperty("progress")
    private BigDecimal progress;

    @JsonProperty("progress_msg")
    private String progressMessage;

    /**
     * <pre>
     * Filter by document processing status. Supports numeric, text, and mixed formats:
     *
     * - Numeric format: `["0", "1", "2", "3", "4"]`
     * - Text format: `[UNSTART, RUNNING, CANCEL, DONE, FAIL]`
     * - Mixed format: `[UNSTART, 1, DONE]` (mixing numeric and text formats)
     * - Status mapping:
     *   - `0` / `UNSTART`: Document not yet processed
     *   - `1` / `RUNNING`: Document is currently being processed
     *   - `2` / `CANCEL`: Document processing was cancelled
     *   - `3` / `DONE`: Document processing completed successfully
     *   - `4` / `FAIL`: Document processing failed
     *     Defaults to all statuses.
     * </pre>
     */
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
    private Long tokenCount;

    @JsonProperty("type")
    private String type;

    @JsonProperty("update_date")
    private String updateDate;

    @JsonProperty("update_time")
    private String updateTime;

    // ----------------------------------------------------------------

    public boolean determineParseIsDone() {
        if (Objects.isNull(run)) {
            return false;
        }

        return "DONE".equalsIgnoreCase(run) || "3".equals(run);
    }
}
