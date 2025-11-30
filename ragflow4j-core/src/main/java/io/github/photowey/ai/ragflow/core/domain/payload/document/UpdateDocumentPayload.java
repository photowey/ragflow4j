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
package io.github.photowey.ai.ragflow.core.domain.payload.document;

import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.photowey.ai.ragflow.core.domain.model.ParserConfig;
import io.github.photowey.ai.ragflow.core.domain.payload.AbstractPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Update document.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#update-document">Update document</a>
 * @since 2025/11/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateDocumentPayload extends AbstractPayload {

    private static final long serialVersionUID = 264205765767138418L;

    @JsonProperty("name")
    @Size(min = 1, max = 255, message = "Document name length must be between 1 and 255 characters")
    private String name;

    @JsonProperty("meta_fields")
    private Map<String, Object> meta;

    /**
     * <pre>
     * - "chunk_method": (Body parameter),`string`
     *   The parsing method to apply to the document:
     *   - `"naive"`: General
     *   - `"manual`: Manual
     *   - `"qa"`: Q&A
     *   - `"table"`: Table
     *   - `"paper"`: Paper
     *   - `"book"`: Book
     *   - `"laws"`: Laws
     *   - `"presentation"`: Presentation
     *   - `"picture"`: Picture
     *   - `"one"`: One
     *   - `"email"`: Email
     * </pre>
     */
    @JsonProperty("chunk_method")
    @Pattern(
        regexp = "^(naive|manual|qa|table|paper|book|laws|presentation|picture|one|email)$",
        message = "Invalid chunk method. Must be one of: naive, manual, qa, table, paper, book, "
            + "laws, presentation, picture, one, or email."
    )
    private String chunkMethod;

    @Valid
    @JsonProperty("parser_config")
    private ParserConfig parserConfig;
}
