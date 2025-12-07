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
package io.github.photowey.ai.ragflow.core.domain.query.document;

import java.io.Serializable;
import java.util.List;

import io.github.photowey.ai.ragflow.core.domain.query.AbstractQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * {@code PollingParsingStatusDocumentQuery}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#list-documents">List documents</a>
 * @since 2025/12/07
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class PollingParsingStatusDocumentQuery extends AbstractQuery implements Serializable {

    private static final long serialVersionUID = -7288970423309549104L;

    /**
     * {@literal }@Nullable.
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
    private List<String> run;
}
