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
package io.github.photowey.ai.ragflow.core.enums;

import java.util.Optional;

/**
 * {@code RAGFlowDictionary}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/22
 */
public enum RAGFlowDictionary {

    ;

    /**
     * ERROR CODES
     * <pre>
     * | Code | Message               | Description                |
     * | ---- | --------------------- | -------------------------- |
     * | 400  | Bad Request           | Invalid request parameters |
     * | 401  | Unauthorized          | Unauthorized access        |
     * | 403  | Forbidden             | Access denied              |
     * | 404  | Not Found             | Resource not found         |
     * | 500  | Internal Server Error | Server internal error      |
     * | 1001 | Invalid Chunk ID      | Invalid Chunk ID           |
     * | 1002 | Chunk Update Failed   | Chunk update failed        |
     * </pre>
     */
    public enum ErrorCode {

        //

        OK("OK", 0),

        // ----------------------------------------------------------------

        BAD_REQUEST("Invalid request parameters", 400),
        UNAUTHORIZED("Unauthorized access", 401),
        FORBIDDEN("Access denied", 403),
        NOT_FOUND("Resource not found", 404),
        INTERNAL_SERVER_ERROR("Server internal error", 500),
        INVALID_CHUNK_ID("Invalid Chunk ID", 1001),
        CHUNK_UPDATE_FAILED("Chunk update failed", 1002);

        private final String description;
        private final int code;

        ErrorCode(String description, int code) {
            this.description = description;
            this.code = code;
        }

        public String description() {
            return description;
        }

        public int code() {
            return code;
        }

        // ----------------------------------------------------------------

        /**
         * Determine if the given code represents an OK status.
         *
         * @param code the error code to check
         * @return true if the code equals OK's code (0), false otherwise
         */
        public static boolean determineIsOk(int code) {
            return OK.code() == code;
        }

        // ----------------------------------------------------------------

        public static Optional<ErrorCode> codeOf(int code) {
            for (ErrorCode errorCode : ErrorCode.values()) {
                if (errorCode.code() == code) {
                    return Optional.of(errorCode);
                }
            }

            return Optional.empty();
        }
    }

    // ----------------------------------------------------------------

    /**
     * <pre>
     * The chunking method of the dataset to create. Available options:
     *
     * - `"naive"`: General (default)
     * - `"book"`: Book
     * - `"email"`: Email
     * - `"laws"`: Laws
     * - `"manual"`: Manual
     * - `"one"`: One
     * - `"paper"`: Paper
     * - `"picture"`: Picture
     * - `"presentation"`: Presentation
     * - `"qa"`: Q&A
     * - `"table"`: Table
     * - `"tag"`: Tag
     * </pre>
     */
    public enum ChunkMethod {

        //

        GENERAL("General", "naive", 1),
        BOOK("Book", "book", 0),
        EMAIL("Email", "email", 0),
        MANUAL("Manual", "manual", 0),
        ONE("One", "one", 0),
        PAPER("Paper", "paper", 0),
        PICTURE("Picture", "picture", 0),
        PRESENTATION("Presentation", "presentation", 0),
        QA("Q&A", "qa", 0),
        TABLE("Table", "table", 0),
        TAG("Tag", "tag", 0),

        ;

        private final String description;
        private final String code;
        private final int defaulted;

        ChunkMethod(String description, String code, int defaulted) {
            this.description = description;
            this.code = code;
            this.defaulted = defaulted;
        }

        public String description() {
            return description;
        }

        public String code() {
            return code;
        }

        public int defaulted() {
            return defaulted;
        }
    }

    // ----------------------------------------------------------------

    public enum API {

        // ---------------------------------------------------------------- DATASET MANAGEMENT

        /**
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
         * </pre>
         */
        CREATE_DATASET("/api/v1/datasets", "POST"),

        /**
         * <pre>
         * ### Delete datasets
         *
         * **DELETE** `/api/v1/datasets`
         *
         * Deletes datasets by ID.
         *
         * #### Request
         *
         * - Method: DELETE
         * - URL: `/api/v1/datasets`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         *   - Body:
         *     - `"ids"`: `list[string]` or `null`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request DELETE \
         *      --url http://{address}/api/v1/datasets \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '{
         *      "ids": ["d94a8dc02c9711f0930f7fbc369eab6d", "e94a8dc02c9711f0930f7fbc369eab6e"]
         *      }'
         * ```
         * </pre>
         */
        DELETE_DATASETS("/api/v1/datasets", "DELETE"),

        /**
         * <pre>
         * ### Update dataset
         *
         * **PUT** `/api/v1/datasets/{dataset_id}`
         *
         * Updates configurations for a specified dataset.
         *
         * #### Request
         *
         * - Method: PUT
         * - URL: `/api/v1/datasets/{dataset_id}`
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
         *   - `"pagerank"`: `int`
         *   - `"parser_config"`: `object`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request PUT \
         *      --url http://{address}/api/v1/datasets/{dataset_id} \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "name": "updated_dataset"
         *      }'
         * ```
         * </pre>
         */
        UPDATE_DATASET("/api/v1/datasets/{dataset_id}", "PUT"),

        /**
         * <pre>
         * ### List datasets
         *
         * **GET** `/api/v1/datasets?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&name={dataset_name}&id={dataset_id}`
         *
         * Lists datasets.
         *
         * #### Request
         *
         * - Method: GET
         * - URL: `/api/v1/datasets?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&name={dataset_name}&id={dataset_id}`
         * - Headers:
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request GET \
         *      --url http://{address}/api/v1/datasets?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&name={dataset_name}&id={dataset_id} \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>'
         * ```
         * </pre>
         */
        LIST_DATASETS("/api/v1/datasets", "GET"),

        /**
         * <pre>
         * ### Get knowledge graph
         *
         * **GET** `/api/v1/datasets/{dataset_id}/knowledge_graph`
         *
         * Retrieves the knowledge graph of a specified dataset.
         *
         * #### Request
         *
         * - Method: GET
         * - URL: `/api/v1/datasets/{dataset_id}/knowledge_graph`
         * - Headers:
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request GET \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/knowledge_graph \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>'
         * ```
         * </pre>
         */
        GET_KNOWLEDGE_GRAPH("/api/v1/datasets/{dataset_id}/knowledge_graph", "GET"),

        /**
         * <pre>
         * ### Delete knowledge graph
         *
         * **DELETE** `/api/v1/datasets/{dataset_id}/knowledge_graph`
         *
         * Removes the knowledge graph of a specified dataset.
         *
         * #### Request
         *
         * - Method: DELETE
         * - URL: `/api/v1/datasets/{dataset_id}/knowledge_graph`
         * - Headers:
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request DELETE \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/knowledge_graph \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>'
         * ```
         * </pre>
         */
        DELETE_KNOWLEDGE_GRAPH("/api/v1/datasets/{dataset_id}/knowledge_graph", "DELETE"),

        // ---------------------------------------------------------------- DATASET MANAGEMENT
        // ---------------------------------------------------------------- FILE MANAGEMENT WITHIN DATASET

        /**
         * <pre>
         * ### Upload documents
         *
         * **POST** `/api/v1/datasets/{dataset_id}/documents`
         *
         * Uploads documents to a specified dataset.
         *
         * #### Request
         *
         * - Method: POST
         * - URL: `/api/v1/datasets/{dataset_id}/documents`
         * - Headers:
         *   - `'Content-Type: multipart/form-data'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Form:
         *   - `'file=@{FILE_PATH}'`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request POST \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/documents \
         *      --header 'Content-Type: multipart/form-data' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --form 'file=@./test1.txt' \
         *      --form 'file=@./test2.pdf'
         * ```
         * </pre>
         */
        UPLOAD_DOCUMENTS("/api/v1/datasets/{dataset_id}/documents", "POST"),

        /**
         * <pre>
         * ### Update document
         *
         * **PUT** `/api/v1/datasets/{dataset_id}/documents/{document_id}`
         *
         * Updates configurations for a specified document.
         *
         * #### Request
         *
         * - Method: PUT
         * - URL: `/api/v1/datasets/{dataset_id}/documents/{document_id}`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"name"`:`string`
         *   - `"meta_fields"`:`object`
         *   - `"chunk_method"`:`string`
         *   - `"parser_config"`:`object`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request PUT \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/documents/{document_id} \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --header 'Content-Type: application/json' \
         *      --data '
         *      {
         *           "name": "manual.txt",
         *           "chunk_method": "manual",
         *           "parser_config": {"chunk_token_num": 128}
         *      }'
         * ```
         * </pre>
         */
        UPDATE_DOCUMENT("/api/v1/datasets/{dataset_id}/documents/{document_id}", "PUT"),

        /**
         * <pre>
         * ### Download document
         *
         * **GET** `/api/v1/datasets/{dataset_id}/documents/{document_id}`
         *
         * Downloads a document from a specified dataset.
         *
         * #### Request
         *
         * - Method: GET
         * - URL: `/api/v1/datasets/{dataset_id}/documents/{document_id}`
         * - Headers:
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Output:
         *   - `'{PATH_TO_THE_FILE}'`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request GET \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/documents/{document_id} \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --output ./ragflow.txt
         * ```
         * </pre>
         */
        DOWNLOAD_DOCUMENT("/api/v1/datasets/{dataset_id}/documents/{document_id}", "GET"),

        /**
         * <pre>
         * ### List documents
         *
         * **GET** `/api/v1/datasets/{dataset_id}/documents?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&keywords={keywords}&id={document_id}&name={document_name}&create_time_from={timestamp}&create_time_to={timestamp}&suffix={file_suffix}&run={run_status}`
         *
         * Lists documents in a specified dataset.
         *
         * #### Request
         *
         * - Method: GET
         * - URL: `/api/v1/datasets/{dataset_id}/documents?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&keywords={keywords}&id={document_id}&name={document_name}&create_time_from={timestamp}&create_time_to={timestamp}&suffix={file_suffix}&run={run_status}`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         *
         * ##### Request examples
         *
         * **A basic request with pagination:**
         *
         * ```bash
         * curl --request GET \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/documents?page=1&page_size=10 \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>'
         * ```
         * </pre>
         */
        LIST_DOCUMENTS("/api/v1/datasets/{dataset_id}/documents", "GET"),

        /**
         * <pre>
         * ### Delete documents
         *
         * **DELETE** `/api/v1/datasets/{dataset_id}/documents`
         *
         * Deletes documents by ID.
         *
         * #### Request
         *
         * - Method: DELETE
         * - URL: `/api/v1/datasets/{dataset_id}/documents`
         * - Headers:
         *   - `'Content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"ids"`: `list[string]`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request DELETE \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/documents \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "ids": ["id_1","id_2"]
         *      }'
         * ```
         * </pre>
         */
        DELETE_DOCUMENTS("/api/v1/datasets/{dataset_id}/documents", "DELETE"),

        /**
         * <pre>
         * ### Parse documents
         *
         * **POST** `/api/v1/datasets/{dataset_id}/chunks`
         *
         * Parses documents in a specified dataset.
         *
         * #### Request
         *
         * - Method: POST
         * - URL: `/api/v1/datasets/{dataset_id}/chunks`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"document_ids"`: `list[string]`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request POST \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/chunks \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "document_ids": ["97a5f1c2759811efaa500242ac120004","97ad64b6759811ef9fc30242ac120004"]
         *      }'
         * ```
         * </pre>
         */
        PARSE_DOCUMENTS("/api/v1/datasets/{dataset_id}/chunks", "POST"),

        /**
         * <pre>
         * ### Stop parsing documents
         *
         * **DELETE** `/api/v1/datasets/{dataset_id}/chunks`
         *
         * Stops parsing specified documents.
         *
         * #### Request
         *
         * - Method: DELETE
         * - URL: `/api/v1/datasets/{dataset_id}/chunks`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"document_ids"`: `list[string]`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request DELETE \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/chunks \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "document_ids": ["97a5f1c2759811efaa500242ac120004","97ad64b6759811ef9fc30242ac120004"]
         *      }'
         * ```
         * </pre>
         */
        STOP_PARSING_DOCUMENTS("/api/v1/datasets/{dataset_id}/chunks", "DELETE"),

        // ---------------------------------------------------------------- FILE MANAGEMENT WITHIN DATASET
        // ---------------------------------------------------------------- CHUNK MANAGEMENT WITHIN DATASET

        /**
         * <pre>
         * ### Add chunk
         *
         * **POST** `/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks`
         *
         * Adds a chunk to a specified document in a specified dataset.
         *
         * #### Request
         *
         * - Method: POST
         * - URL: `/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"content"`: `string`
         *   - `"important_keywords"`: `list[string]`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request POST \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "content": "<CHUNK_CONTENT_HERE>"
         *      }'
         * ```
         * </pre>
         */
        ADD_CHUNK("/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks", "POST"),

        /**
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
         * </pre>
         */
        LIST_CHUNKS("/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks", "GET"),

        /**
         * <pre>
         * ### Delete chunks
         *
         * **DELETE** `/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks`
         *
         * Deletes chunks by ID.
         *
         * #### Request
         *
         * - Method: DELETE
         * - URL: `/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"chunk_ids"`: `list[string]`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request DELETE \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "chunk_ids": ["test_1", "test_2"]
         *      }'
         * ```
         * </pre>
         */
        DELETE_CHUNKS("/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks", "DELETE"),

        /**
         * <pre>
         * ### Update chunk
         *
         * **PUT** `/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks/{chunk_id}`
         *
         * Updates content or configurations for a specified chunk.
         *
         * #### Request
         *
         * - Method: PUT
         * - URL: `/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks/{chunk_id}`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"content"`: `string`
         *   - `"important_keywords"`: `list[string]`
         *   - `"available"`: `boolean`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request PUT \
         *      --url http://{address}/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks/{chunk_id} \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "content": "ragflow123",
         *           "important_keywords": []
         *      }'
         * ```
         * </pre>
         */
        UPDATE_CHUNK("/api/v1/datasets/{dataset_id}/documents/{document_id}/chunks/{chunk_id}", "PUT"),

        /**
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
         */
        RETRIEVE_CHUNKS("/api/v1/retrieval", "POST"),

        // ---------------------------------------------------------------- CHUNK MANAGEMENT WITHIN DATASET
        // ---------------------------------------------------------------- CHAT ASSISTANT MANAGEMENT

        /**
         * <pre>
         * ### Create chat assistant
         *
         * **POST** `/api/v1/chats`
         *
         * Creates a chat assistant.
         *
         * #### Request
         *
         * - Method: POST
         * - URL: `/api/v1/chats`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"name"`: `string`
         *   - `"avatar"`: `string`
         *   - `"dataset_ids"`: `list[string]`
         *   - `"llm"`: `object`
         *   - `"prompt"`: `object`
         *
         * ##### Request example
         *
         * ```shell
         * curl --request POST \
         *      --url http://{address}/api/v1/chats \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '{
         *     "dataset_ids": ["0b2cbc8c877f11ef89070242ac120005"],
         *     "name":"new_chat_1"
         * }'
         * ```
         * </pre>
         */
        CREATE_CHAT_ASSISTANT("/api/v1/chats", "POST"),

        /**
         * <pre>
         * ### Update chat assistant
         *
         * **PUT** `/api/v1/chats/{chat_id}`
         *
         * Updates configurations for a specified chat assistant.
         *
         * #### Request
         *
         * - Method: PUT
         * - URL: `/api/v1/chats/{chat_id}`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"name"`: `string`
         *   - `"avatar"`: `string`
         *   - `"dataset_ids"`: `list[string]`
         *   - `"llm"`: `object`
         *   - `"prompt"`: `object`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request PUT \
         *      --url http://{address}/api/v1/chats/{chat_id} \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "name":"Test"
         *      }'
         * ```
         * </pre>
         */
        UPDATE_CHAT_ASSISTANT("/api/v1/chats/{chat_id}", "PUT"),

        /**
         * <pre>
         * ### Delete chat assistants
         *
         * **DELETE** `/api/v1/chats`
         *
         * Deletes chat assistants by ID.
         *
         * #### Request
         *
         * - Method: DELETE
         * - URL: `/api/v1/chats`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"ids"`: `list[string]`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request DELETE \
         *      --url http://{address}/api/v1/chats \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "ids": ["test_1", "test_2"]
         *      }'
         * ```
         * </pre>
         */
        DELETE_CHAT_ASSISTANTS("/api/v1/chats", "DELETE"),

        /**
         * <pre>
         * ### List chat assistants
         *
         * **GET** `/api/v1/chats?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&name={chat_name}&id={chat_id}`
         *
         * Lists chat assistants.
         *
         * #### Request
         *
         * - Method: GET
         * - URL: `/api/v1/chats?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&name={chat_name}&id={chat_id}`
         * - Headers:
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request GET \
         *      --url http://{address}/api/v1/chats?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&name={chat_name}&id={chat_id} \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>'
         * ```
         * </pre>
         */
        LIST_CHAT_ASSISTANTS("/api/v1/chats", "GET"),

        // ---------------------------------------------------------------- CHAT ASSISTANT MANAGEMENT
        // ---------------------------------------------------------------- SESSION MANAGEMENT

        /**
         * <pre>
         * ### Create session with chat assistant
         *
         * **POST** `/api/v1/chats/{chat_id}/sessions`
         *
         * Creates a session with a chat assistant.
         *
         * #### Request
         *
         * - Method: POST
         * - URL: `/api/v1/chats/{chat_id}/sessions`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"name"`: `string`
         *   - `"user_id"`: `string` (optional)
         *
         * ##### Request example
         *
         * ```bash
         * curl --request POST \
         *      --url http://{address}/api/v1/chats/{chat_id}/sessions \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "name": "new session"
         *      }'
         * ```
         * </pre>
         */
        CREATE_CHAT_SESSION("/api/v1/chats/{chat_id}/sessions", "POST"),

        /**
         * <pre>
         * ### Update chat assistant's session
         *
         * **PUT** `/api/v1/chats/{chat_id}/sessions/{session_id}`
         *
         * Updates a session of a specified chat assistant.
         *
         * #### Request
         *
         * - Method: PUT
         * - URL: `/api/v1/chats/{chat_id}/sessions/{session_id}`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"name`: `string`
         *   - `"user_id`: `string` (optional)
         *
         * ##### Request example
         *
         * ```bash
         * curl --request PUT \
         *      --url http://{address}/api/v1/chats/{chat_id}/sessions/{session_id} \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "name": "<REVISED_SESSION_NAME_HERE>"
         *      }'
         * ```
         * </pre>
         */
        UPDATE_CHAT_SESSION("/api/v1/chats/{chat_id}/sessions/{session_id}", "PUT"),

        /**
         * <pre>
         * ### List chat assistant's sessions
         *
         * **GET** `/api/v1/chats/{chat_id}/sessions?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&name={session_name}&id={session_id}`
         *
         * Lists sessions associated with a specified chat assistant.
         *
         * #### Request
         *
         * - Method: GET
         * - URL: `/api/v1/chats/{chat_id}/sessions?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&name={session_name}&id={session_id}&user_id={user_id}`
         * - Headers:
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request GET \
         *      --url http://{address}/api/v1/chats/{chat_id}/sessions?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&name={session_name}&id={session_id} \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>'
         * ```
         * </pre>
         */
        LIST_CHAT_SESSIONS("/api/v1/chats/{chat_id}/sessions", "GET"),

        /**
         * <pre>
         * ### Delete chat assistant's sessions
         *
         * **DELETE** `/api/v1/chats/{chat_id}/sessions`
         *
         * Deletes sessions of a chat assistant by ID.
         *
         * #### Request
         *
         * - Method: DELETE
         * - URL: `/api/v1/chats/{chat_id}/sessions`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"ids"`: `list[string]`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request DELETE \
         *      --url http://{address}/api/v1/chats/{chat_id}/sessions \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "ids": ["test_1", "test_2"]
         *      }'
         * ```
         * </pre>
         */
        DELETE_CHAT_SESSIONS("/api/v1/chats/{chat_id}/sessions", "DELETE"),

        /**
         * <pre>
         * ### Converse with chat assistant
         *
         * **POST** `/api/v1/chats/{chat_id}/completions`
         *
         * Asks a specified chat assistant a question to start an AI-powered conversation.
         *
         * :::tip NOTE
         *
         * - In streaming mode, not all responses include a reference, as this depends on the system's judgement.
         *
         * - In streaming mode, the last message is an empty message:
         *
         *   ```json
         *   data:
         *   {
         *     "code": 0,
         *     "data": true
         *   }
         *   ```
         *
         * :::
         *
         * #### Request
         *
         * - Method: POST
         * - URL: `/api/v1/chats/{chat_id}/completions`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"question"`: `string`
         *   - `"stream"`: `boolean`
         *   - `"session_id"`: `string` (optional)
         *   - `"user_id`: `string` (optional)
         *
         * ##### Request example
         *
         * ```bash
         * curl --request POST \
         *      --url http://{address}/api/v1/chats/{chat_id}/completions \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data-binary '
         *      {
         *      }'
         * curl --request POST \
         *      --url http://{address}/api/v1/chats/{chat_id}/completions \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data-binary '
         *      {
         *           "question": "Who are you",
         *           "stream": true,
         *           "session_id":"9fa7691cb85c11ef9c5f0242ac120005"
         *      }'
         * ```
         * </pre>
         */
        CONVERSE_CHAT_SESSION("/api/v1/chats/{chat_id}/completions", "POST"),

        /**
         * <pre>
         * ### Create session with agent
         *
         * :::danger DEPRECATED This method is deprecated and not recommended. You can still call it but be mindful that calling `Converse with agent` will automatically generate a session ID for the associated agent. :::
         *
         * **POST** `/api/v1/agents/{agent_id}/sessions`
         *
         * Creates a session with an agent.
         *
         * #### Request
         *
         * - Method: POST
         * - URL: `/api/v1/agents/{agent_id}/sessions?user_id={user_id}`
         * - Headers:
         *   - `'content-Type: application/json'
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - the required parameters:`str`
         *   - other parameters: The variables specified in the **Begin** component.
         *
         * ##### Request example
         *
         * If the **Begin** component in your agent does not take required parameters:
         *
         * ```bash
         * curl --request POST \
         *      --url http://{address}/api/v1/agents/{agent_id}/sessions \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '{
         *      }'
         * ```
         * </pre>
         */
        CREATE_AGENT_SESSION("/api/v1/agents/{agent_id}/sessions", "POST"),

        /**
         * <pre>
         * ### Converse with agent
         *
         * **POST** `/api/v1/agents/{agent_id}/completions`
         *
         * Asks a specified agent a question to start an AI-powered conversation.
         *
         * :::tip NOTE
         *
         * - In streaming mode, not all responses include a reference, as this depends on the system's judgement.
         *
         * - In streaming mode, the last message is an empty message:
         *
         *   ```
         *   [DONE]
         *   ```
         *
         * :::
         *
         * #### Request
         *
         * - Method: POST
         * - URL: `/api/v1/agents/{agent_id}/completions`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"question"`: `string`
         *   - `"stream"`: `boolean`
         *   - `"session_id"`: `string` (optional)
         *   - `"inputs"`: `object` (optional)
         *   - `"user_id"`: `string` (optional)
         *
         * :::info IMPORTANT You can include custom parameters in the request body, but first ensure they are defined in the [Begin](http://192.168.1.2/guides/agent/agent_component_reference/begin.mdx) component. :::
         *
         * ##### Request example
         *
         * - If the **Begin** component does not take parameters:
         *
         * ```bash
         * curl --request POST \
         *      --url http://{address}/api/v1/agents/{agent_id}/completions \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data-binary '
         *      {
         *         "question": "Hello",
         *         "stream": false,
         *      }'
         * ```
         *
         * - If the **Begin** component takes parameters, include their values in the body of `"inputs"` as follows:
         *
         * ```bash
         * curl --request POST \
         *      --url http://{address}/api/v1/agents/{agent_id}/completions \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data-binary '
         *     {
         *         "question": "Hello",
         *         "stream": false,
         *         "inputs": {
         *             "line_var": {
         *                 "type": "line",
         *                 "value": "I am line_var"
         *             },
         *             "int_var": {
         *                 "type": "integer",
         *                 "value": 1
         *             },
         *             "paragraph_var": {
         *                 "type": "paragraph",
         *                 "value": "a\nb\nc"
         *             },
         *             "option_var": {
         *                 "type": "options",
         *                 "value": "option 2"
         *             },
         *             "boolean_var": {
         *                 "type": "boolean",
         *                 "value": true
         *             }
         *         }
         *     }'
         * ```
         *
         * The following code will execute the completion process
         *
         * ```bash
         * curl --request POST \
         *      --url http://{address}/api/v1/agents/{agent_id}/completions \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data-binary '
         *      {
         *           "question": "Hello",
         *           "stream": true,
         *           "session_id": "cb2f385cb86211efa36e0242ac120005"
         *      }'
         * ```
         * </pre>
         */
        CONVERSE_AGENT_SESSION("/api/v1/agents/{agent_id}/completions", "POST"),

        /**
         * <pre>
         * ### List agent sessions
         *
         * **GET** `/api/v1/agents/{agent_id}/sessions?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&id={session_id}&user_id={user_id}&dsl={dsl}`
         *
         * Lists sessions associated with a specified agent.
         *
         * #### Request
         *
         * - Method: GET
         * - URL: `/api/v1/agents/{agent_id}/sessions?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&id={session_id}`
         * - Headers:
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request GET \
         *      --url http://{address}/api/v1/agents/{agent_id}/sessions?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&id={session_id}&user_id={user_id} \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>'
         * ```
         * </pre>
         */
        LIST_AGENT_SESSIONS("/api/v1/agents/{agent_id}/sessions", "GET"),

        /**
         * <pre>
         * ### Delete agent's sessions
         *
         * **DELETE** `/api/v1/agents/{agent_id}/sessions`
         *
         * Deletes sessions of a agent by ID.
         *
         * #### Request
         *
         * - Method: DELETE
         * - URL: `/api/v1/agents/{agent_id}/sessions`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"ids"`: `list[string]`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request DELETE \
         *      --url http://{address}/api/v1/agents/{agent_id}/sessions \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '
         *      {
         *           "ids": ["test_1", "test_2"]
         *      }'
         * ```
         * </pre>
         */
        DELETE_AGENT_SESSIONS("/api/v1/agents/{agent_id}/sessions", "DELETE"),


        /**
         * <pre>
         * ### Generate related questions
         *
         * **POST** `/api/v1/sessions/related_questions`
         *
         * Generates five to ten alternative question strings from the user's original query to retrieve more relevant search results.
         *
         * This operation requires a `Bearer Login Token`, which typically expires with in 24 hours. You can find the it in the Request Headers in your browser easily as shown below:
         *
         * ![Image](https://raw.githubusercontent.com/infiniflow/ragflow-docs/main/images/login_token.jpg)
         *
         * :::tip NOTE The chat model autonomously determines the number of questions to generate based on the instruction, typically between five and ten. :::
         *
         * #### Request
         *
         * - Method: POST
         * - URL: `/api/v1/sessions/related_questions`
         * - Headers:
         *   - `'content-Type: application/json'`
         *   - `'Authorization: Bearer <YOUR_LOGIN_TOKEN>'`
         * - Body:
         *   - `"question"`: `string`
         *   - `"industry"`: `string`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request POST \
         *      --url http://{address}/api/v1/sessions/related_questions \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_LOGIN_TOKEN>' \
         *      --data '
         *      {
         *           "question": "What are the key advantages of Neovim over Vim?",
         *           "industry": "software_development"
         *      }'
         * ```
         * </pre>
         */
        GENERATE_RELATED_QUESTIONS("/api/v1/sessions/related_questions", "POST"),

        // ---------------------------------------------------------------- SESSION MANAGEMENT
        // ---------------------------------------------------------------- AGENT MANAGEMENT

        /**
         * <pre>
         * ### List agents
         *
         * **GET** `/api/v1/agents?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&name={agent_name}&id={agent_id}`
         *
         * Lists agents.
         *
         * #### Request
         *
         * - Method: GET
         * - URL: `/api/v1/agents?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&title={agent_name}&id={agent_id}`
         * - Headers:
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request GET \
         *      --url http://{address}/api/v1/agents?page={page}&page_size={page_size}&orderby={orderby}&desc={desc}&title={agent_name}&id={agent_id} \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>'
         * ```
         * </pre>
         */
        LIST_AGENTS("/api/v1/agents", "GET"),

        /**
         * <pre>
         * ### Create agent
         *
         * **POST** `/api/v1/agents`
         *
         * Create an agent.
         *
         * #### Request
         *
         * - Method: POST
         * - URL: `/api/v1/agents`
         * - Headers:
         *   - `'Content-Type: application/json`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"title"`: `string`
         *   - `"description"`: `string`
         *   - `"dsl"`: `object`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request POST \
         *      --url http://{address}/api/v1/agents \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '{
         *          "title": "Test Agent",
         *          "description": "A test agent",
         *          "dsl": {
         *            // ... Canvas DSL here ...
         *          }
         *      }'
         * ```
         * </pre>
         */
        CREATE_AGENT("/api/v1/agents", "POST"),

        /**
         * <pre>
         * ### Update agent
         *
         * **PUT** `/api/v1/agents/{agent_id}`
         *
         * Update an agent by id.
         *
         * #### Request
         *
         * - Method: PUT
         * - URL: `/api/v1/agents/{agent_id}`
         * - Headers:
         *   - `'Content-Type: application/json`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         * - Body:
         *   - `"title"`: `string`
         *   - `"description"`: `string`
         *   - `"dsl"`: `object`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request PUT \
         *      --url http://{address}/api/v1/agents/58af890a2a8911f0a71a11b922ed82d6 \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '{
         *          "title": "Test Agent",
         *          "description": "A test agent",
         *          "dsl": {
         *            // ... Canvas DSL here ...
         *          }
         *      }'
         * ```
         * </pre>
         */
        UPDATE_AGENT("/api/v1/agents/{agent_id}", "PUT"),

        /**
         * <pre>
         * ### Delete agent
         *
         * **DELETE** `/api/v1/agents/{agent_id}`
         *
         * Delete an agent by id.
         *
         * #### Request
         *
         * - Method: DELETE
         * - URL: `/api/v1/agents/{agent_id}`
         * - Headers:
         *   - `'Content-Type: application/json`
         *   - `'Authorization: Bearer <YOUR_API_KEY>'`
         *
         * ##### Request example
         *
         * ```bash
         * curl --request DELETE \
         *      --url http://{address}/api/v1/agents/58af890a2a8911f0a71a11b922ed82d6 \
         *      --header 'Content-Type: application/json' \
         *      --header 'Authorization: Bearer <YOUR_API_KEY>' \
         *      --data '{}'
         * ```
         * </pre>
         */
        DELETE_AGENT("/api/v1/agents/{agent_id}", "DELETE"),

        // ---------------------------------------------------------------- AGENT MANAGEMENT
        // ---------------------------------------------------------------- SYSTEM

        /**
         * <pre>
         * ### Check system health
         *
         * **GET** `/v1/system/healthz`
         *
         * Check the health status of RAGFlow’s dependencies (database, Redis, document engine, object storage).
         *
         * #### Request
         *
         * - Method: GET
         * - URL: `/v1/system/healthz`
         * - Headers:
         *   - 'Content-Type: application/json' (no Authorization required)
         *
         * ##### Request example
         *
         * ```bash
         * curl --request GET
         *      --url http://{address}/v1/system/healthz
         *      --header 'Content-Type: application/json'
         * ```
         * </pre>
         */
        CHECK_SYSTEM_HEALTH("/v1/system/healthz", "GET"),

        // ---------------------------------------------------------------- SYSTEM

        ;

        private final String route;
        private final String method;

        API(String route, String method) {
            this.route = route;
            this.method = method;
        }

        // ----------------------------------------------------------------

        public String route() {
            return route;
        }

        public String method() {
            return method;
        }
    }

}
