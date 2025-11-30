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

import io.github.photowey.ai.ragflow.core.domain.context.document.DeleteDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.DownloadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.ListDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.ParseDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.StopParsingDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UpdateDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UploadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.dto.document.DeleteDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.DownloadDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.ListDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.ParseDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.StopParsingDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.UpdateDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.UploadDocumentDTO;

/**
 * {@code RAGFlowDocumentClient}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/11/30
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public interface RAGFlowDocumentClient {

    /**
     * Upload documents.
     *
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
     *
     * @param context {@link UploadDocumentContext}
     * @return {@link UploadDocumentDTO}
     */
    UploadDocumentDTO uploadDocuments(@NotNull UploadDocumentContext context);

    /**
     * Update document.
     *
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
     *
     * @param context {@link UpdateDocumentContext}
     * @return {@link UpdateDocumentDTO}
     */
    UpdateDocumentDTO updateDocument(@NotNull UpdateDocumentContext context);

    /**
     * Download document.
     *
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
     *
     * @param context {@link DownloadDocumentContext}
     * @return {@link DownloadDocumentDTO}
     */
    DownloadDocumentDTO downloadDocument(@NotNull DownloadDocumentContext context);

    /**
     * List documents.
     *
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
     *
     * @param context {@link ListDocumentContext}
     * @return {@link ListDocumentDTO}
     */
    ListDocumentDTO listDocuments(@NotNull ListDocumentContext context);

    /**
     * Delete documents.
     *
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
     *
     * @param context {@link DeleteDocumentContext}
     * @return {@link DeleteDocumentDTO}
     */
    DeleteDocumentDTO deleteDocuments(@NotNull DeleteDocumentContext context);

    /**
     * Parse documents.
     *
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
     *
     * @param context {@link ParseDocumentContext}
     * @return {@link ParseDocumentDTO}
     */
    ParseDocumentDTO parseDocuments(@NotNull ParseDocumentContext context);

    /**
     * Stop parsing documents.
     *
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
     *
     * @param context {@link StopParsingDocumentContext}
     * @return {@link StopParsingDocumentDTO}
     */
    StopParsingDocumentDTO stopParsingDocuments(@NotNull StopParsingDocumentContext context);
}
