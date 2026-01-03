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

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code Condition}.
 *
 * @author photowey
 * @version 2025.0.22.1.1
 * @since 2026/01/02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Condition implements Serializable {

    private static final long serialVersionUID = 9128289949807095427L;

    private String name;
    /**
     * The comparison operator.
     * <pre>
     * The comparison operator. Can be one of:
     *   - `"contains"`
     *   - `"not contains"`
     *   - `"start with"`
     *   - `"empty"`
     *   - `"not empty"`
     *   - `"="`
     *   - `"≠"`
     *   - `"&gt;"`
     *   - `"&lt;"`
     *   - `"&gt;="`
     *   - `"&lt;="`
     * </pre>
     */
    @JsonProperty("comparison_operator")
    private String comparisonOperator;
    private String value;

    // ----------------------------------------------------------------

    public String name() {
        return name;
    }

    public String comparisonOperator() {
        return comparisonOperator;
    }

    public String value() {
        return value;
    }
}
