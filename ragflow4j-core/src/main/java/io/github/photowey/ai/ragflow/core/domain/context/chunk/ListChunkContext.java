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
package io.github.photowey.ai.ragflow.core.domain.context.chunk;

import jakarta.validation.constraints.NotBlank;

import io.github.photowey.ai.ragflow.core.domain.context.AbstractContext;
import io.github.photowey.ai.ragflow.core.domain.query.chunk.ListChunkQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * List chunks.
 *
 * @author photowey
 * @version 2025.0.22.1.1
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#list-chunks">List chunks</a>
 * @since 2026/01/02
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class ListChunkContext extends AbstractContext {

    private static final long serialVersionUID = 6244524610092587379L;

    @NotBlank(message = "documentId must not be blank")
    private String documentId;
    private ListChunkQuery query;

    public ListChunkQuery toQuery() {
        return this.query();
    }
}
