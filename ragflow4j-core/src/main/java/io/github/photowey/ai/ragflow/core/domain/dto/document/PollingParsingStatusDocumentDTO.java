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

import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.github.photowey.ai.ragflow.core.domain.dto.MetadataDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * {@code PollingParsingStatusDocumentDTO}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/12/07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PollingParsingStatusDocumentDTO extends MetadataDTO {

    private static final long serialVersionUID = -2988922111452487333L;

    private static final String PARSING_DONE = "DONE";
    private static final String PARSING_DONE_STATUS = "3";

    private static final String PARSING_CANCELED = "CANCEL";
    private static final String PARSING_CANCELED_STATUS = "2";

    @NotBlank
    private String documentId;

    /**
     * Parsing status.
     *
     * <pre>
     * Filter by document processing status. Supports numeric, text, and mixed formats:
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
    @NotBlank
    private String status;

    // ----------------------------------------------------------------

    public boolean hasParsingFinished() {
        return PARSING_DONE.equalsIgnoreCase(this.status) || PARSING_DONE_STATUS.equals(this.status);
    }

    public boolean hasParsingCancelled() {
        return PARSING_CANCELED.equalsIgnoreCase(this.status) || PARSING_CANCELED_STATUS.equals(this.status);
    }
}
