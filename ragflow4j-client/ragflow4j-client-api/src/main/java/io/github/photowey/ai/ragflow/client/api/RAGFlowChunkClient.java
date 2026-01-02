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

import jakarta.validation.constraints.NotNull;

import io.github.photowey.ai.ragflow.core.domain.context.chunk.ListChunkContext;
import io.github.photowey.ai.ragflow.core.domain.context.chunk.RetrieveChunkContext;
import io.github.photowey.ai.ragflow.core.domain.dto.chunk.ListChunkDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.chunk.RetrieveChunkDTO;

/**
 * {@code RAGFlowChunkClient}.
 *
 * @author photowey
 * @version 2025.0.22.1.1
 * @since 2026/01/02
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public interface RAGFlowChunkClient {

    /**
     * List chunks.
     *
     * <pre>
     * ### List chunks
     *
     * **GET** `/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks?keywords={keywords}&page={page}&page_size={page_size}&id={id}`
     *
     * Lists chunks in a specified document.
     *
     * #### Request
     *
     * - Method: GET
     * - URL: `/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks?keywords={keywords}&page={page}&page_size={page_size}&id={chunk_id}`
     * - Headers:
     *   - `'Authorization: Bearer <YOUR_API_KEY>'`
     *
     * ##### Request example
     *
     * ```bash
     * curl --request GET \
     *      --url http://{address}/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks?keywords={keywords}&page={page}&page_size={page_size}&id={chunk_id} \
     *      --header 'Authorization: Bearer <YOUR_API_KEY>'
     * ```
     *
     *
     *
     * ##### Request parameters
     *
     * - `dataset_id`: (*Path parameter*)
     *   The associated dataset ID.
     * - `document_id`: (*Path parameter*)
     *   The associated document ID.
     * - `keywords`(*Filter parameter*), `string`
     *   The keywords used to match chunk content.
     * - `page`(*Filter parameter*), `integer`
     *   Specifies the page on which the chunks will be displayed. Defaults to `1`.
     * - `page_size`(*Filter parameter*), `integer`
     *   The maximum number of chunks on each page. Defaults to `1024`.
     * - `id`(*Filter parameter*), `string`
     *   The ID of the chunk to retrieve.
     *
     * #### Response
     *
     * Success:
     *
     * ```json
     * {
     *     "code": 0,
     *     "data": {
     *         "chunks": [
     *             {
     *                 "available": true,
     *                 "content": "This is a test content.",
     *                 "docnm_kwd": "1.txt",
     *                 "document_id": "b330ec2e91ec11efbc510242ac120004",
     *                 "id": "b48c170e90f70af998485c1065490726",
     *                 "image_id": "",
     *                 "important_keywords": "",
     *                 "positions": [
     *                     ""
     *                 ]
     *             }
     *         ],
     *         "doc": {
     *             "chunk_count": 1,
     *             "chunk_method": "naive",
     *             "create_date": "Thu, 24 Oct 2024 09:45:27 GMT",
     *             "create_time": 1729763127646,
     *             "created_by": "69736c5e723611efb51b0242ac120007",
     *             "dataset_id": "527fa74891e811ef9c650242ac120006",
     *             "id": "b330ec2e91ec11efbc510242ac120004",
     *             "location": "1.txt",
     *             "name": "1.txt",
     *             "parser_config": {
     *                 "chunk_token_num": 128,
     *                 "delimiter": "\\n",
     *                 "html4excel": false,
     *                 "layout_recognize": true,
     *                 "raptor": {
     *                     "use_raptor": false
     *                 }
     *             },
     *             "process_begin_at": "Thu, 24 Oct 2024 09:56:44 GMT",
     *             "process_duration": 0.54213,
     *             "progress": 0.0,
     *             "progress_msg": "Task dispatched...",
     *             "run": "2",
     *             "size": 17966,
     *             "source_type": "local",
     *             "status": "1",
     *             "thumbnail": "",
     *             "token_count": 8,
     *             "type": "doc",
     *             "update_date": "Thu, 24 Oct 2024 11:03:15 GMT",
     *             "update_time": 1729767795721
     *         },
     *         "total": 1
     *     }
     * }
     * ```
     * </pre>
     *
     * @param context {@link ListChunkContext}
     * @return {@link ListChunkDTO}
     */
    ListChunkDTO listChunks(@NotNull ListChunkContext context);

    /**
     * Retrieve chunks.
     *
     * <pre>
     * ### Retrieve chunks
     *
     * **POST** `/api/v1/retrieval`
     *
     * Retrieves chunks from specified datasets.
     *
     * #### Request
     *
     * - Method: POST
     * - URL: `/api/v1/retrieval`
     * - Headers:
     *   - `'content-Type: application/json'`
     *   - `'Authorization: Bearer <YOUR_API_KEY>'`
     * - Body:
     *   - `"question"`: `string`
     *   - `"dataset_ids"`: `list[string]`
     *   - `"document_ids"`: `list[string]`
     *   - `"page"`: `integer`
     *   - `"page_size"`: `integer`
     *   - `"similarity_threshold"`: `float`
     *   - `"vector_similarity_weight"`: `float`
     *   - `"top_k"`: `integer`
     *   - `"rerank_id"`: `string`
     *   - `"keyword"`: `boolean`
     *   - `"highlight"`: `boolean`
     *   - `"cross_languages"`: `list[string]`
     *   - `"metadata_condition"`: `object`
     *   - `"use_kg"`: `boolean`
     *
     * ##### Request example
     *
     * ```bash
     * curl --request POST \
     *      --url http://{address}/api/v1/retrieval \
     *      --header 'Content-Type: application/json' \
     *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
     *      --data '
     *      {
     *           "question": "What is advantage of ragflow?",
     *           "dataset_ids": ["b2a62730759d11ef987d0242ac120004"],
     *           "document_ids": ["77df9ef4759a11ef8bdd0242ac120004"],
     *           "metadata_condition": {
     *             "conditions": [
     *               {
     *                 "name": "author",
     *                 "comparison_operator": "=",
     *                 "value": "Toby"
     *               },
     *               {
     *                 "name": "url",
     *                 "comparison_operator": "not contains",
     *                 "value": "amd"
     *               }
     *             ]
     *           }
     *      }'
     * ```
     * </pre>
     *
     * @param context {@link RetrieveChunkContext}
     * @return {@link RetrieveChunkDTO}
     */
    RetrieveChunkDTO retrieveChunks(@NotNull RetrieveChunkContext context);
}
