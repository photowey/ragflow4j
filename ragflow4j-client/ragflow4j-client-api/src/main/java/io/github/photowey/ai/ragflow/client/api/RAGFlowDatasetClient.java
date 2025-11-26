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
package io.github.photowey.ai.ragflow.client.api;

import io.github.photowey.ai.ragflow.core.domain.context.CreateDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.CreateDatasetDTO;

/**
 * {@code RAGFlowDatasetClient}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public interface RAGFlowDatasetClient {

    /**
     * Create dataset.
     *
     * <pre>
     * ### Create dataset
     *
     * **POST** `/api/v1/datasets`
     *
     * Creates a dataset.
     *
     * #### Request
     *
     * - Method: POST
     * - URL: `/api/v1/datasets`
     * - Headers:
     *   - `'content-Type: application/json'`
     *   - `'Authorization: Bearer <YOUR_API_KEY>'`
     * - Body:
     *   - `"name"`: `string`
     *   - `"avatar"`: `string`
     *   - `"description"`: `string`
     *   - `"embedding_model"`: `string`
     *   - `"permission"`: `string`
     *   - `"chunk_method"`: `string`
     *   - `"parser_config"`: `object`
     *
     * ##### Request example
     *
     * ```bash
     * curl --request POST \
     *      --url http://{address}/api/v1/datasets \
     *      --header 'Content-Type: application/json' \
     *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
     *      --data '{
     *       "name": "test_1"
     *       }'
     * ```
     *
     * ##### Request parameters
     *
     * - `"name"`: (*Body parameter*), `string`, *Required*
     *   The unique name of the dataset to create. It must adhere to the following requirements:
     *
     *   - Basic Multilingual Plane (BMP) only
     *   - Maximum 128 characters
     *   - Case-insensitive
     *
     * - `"avatar"`: (*Body parameter*), `string`
     *   Base64 encoding of the avatar.
     *
     *   - Maximum 65535 characters
     *
     * - `"description"`: (*Body parameter*), `string`
     *   A brief description of the dataset to create.
     *
     *   - Maximum 65535 characters
     *
     * - `"embedding_model"`: (*Body parameter*), `string`
     *   The name of the embedding model to use. For example: `"BAAI/bge-large-zh-v1.5@BAAI"`
     *
     *   - Maximum 255 characters
     *   - Must follow `model_name@model_factory` format
     *
     * - `"permission"`: (*Body parameter*), `string`
     *   Specifies who can access the dataset to create. Available options:
     *
     *   - `"me"`: (Default) Only you can manage the dataset.
     *   - `"team"`: All team members can manage the dataset.
     *
     * - `"chunk_method"`: (*Body parameter*), `enum<string>`
     *   The chunking method of the dataset to create. Available options:
     *
     *   - `"naive"`: General (default)
     *   - `"book"`: Book
     *   - `"email"`: Email
     *   - `"laws"`: Laws
     *   - `"manual"`: Manual
     *   - `"one"`: One
     *   - `"paper"`: Paper
     *   - `"picture"`: Picture
     *   - `"presentation"`: Presentation
     *   - `"qa"`: Q&A
     *   - `"table"`: Table
     *   - `"tag"`: Tag
     *
     * - `"parser_config"`: (*Body parameter*), `object`
     *   The configuration settings for the dataset parser. The attributes in this JSON object vary with the selected `"chunk_method"`
     * </pre>
     *
     * @param context {@link CreateDatasetContext}
     * @return {@link CreateDatasetDTO}
     */
    CreateDatasetDTO createDataset(CreateDatasetContext context);
}
