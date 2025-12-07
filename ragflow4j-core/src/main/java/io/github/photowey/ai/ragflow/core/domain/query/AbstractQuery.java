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
package io.github.photowey.ai.ragflow.core.domain.query;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@code AbstractQuery}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/26
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The ID of the $target(dataset|document|...) to retrieve.
     */
    protected String id;

    /**
     * The name of the $target(dataset|document|...) to retrieve.
     */
    protected String name;
    /**
     * The search keywords of the $target(dataset|document|...) to retrieve.
     */
    protected String keywords;

    // ----------------------------------------------------------------

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String keywords() {
        return keywords;
    }

    public void ensureIdNotBlank() {
        if (Objects.isNull(this.id()) || this.id().trim().isEmpty()) {
            throw new IllegalArgumentException("DocumentId cannot be null");
        }
    }
}
