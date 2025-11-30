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
package io.github.photowey.ai.ragflow.core.domain.payload.dataset;

import java.io.Serializable;

import lombok.Data;

/**
 * Delete knowledge graph.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/29
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#delete-knowledge-graph">Delete knowledge graph</a>
 */
@Data
public class DeleteKnowledgeGraphPayload implements Serializable {

    private static final long serialVersionUID = -8996419124428635153L;
}
