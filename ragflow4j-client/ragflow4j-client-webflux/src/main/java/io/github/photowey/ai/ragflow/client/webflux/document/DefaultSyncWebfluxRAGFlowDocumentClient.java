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
package io.github.photowey.ai.ragflow.client.webflux.document;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;

import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.core.constant.MessageConstants;
import io.github.photowey.ai.ragflow.core.domain.context.document.DeleteDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.DownloadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.ListDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.ParseDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.PollingParsingStatusDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.StopParsingDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UpdateDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UploadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.download.DownloadHandle;
import io.github.photowey.ai.ragflow.core.domain.dto.document.DeleteDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.DocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.ListDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.ParseDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.PollingParsingStatusDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.StopParsingDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.UpdateDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.UploadDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.model.response.RAGFlowResponse;
import io.github.photowey.ai.ragflow.core.domain.query.document.ListDocumentQuery;
import io.github.photowey.ai.ragflow.core.domain.query.document.PollingParsingStatusDocumentQuery;
import io.github.photowey.ai.ragflow.core.exception.RAGFlowException;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import reactor.core.publisher.Mono;

/**
 * {@code DefaultSyncWebfluxRAGFlowDocumentClient}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/30
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class DefaultSyncWebfluxRAGFlowDocumentClient
    extends AbstractWebfluxRAGFlowDocumentClient implements SyncWebfluxRAGFlowDocumentClient {

    public DefaultSyncWebfluxRAGFlowDocumentClient(
        RAGFlowPropertiesGetter getter,
        RAGFlowWebClientFactory factory) {
        super(getter, factory);
    }

    @Override
    public List<UploadDocumentDTO> uploadDocuments(UploadDocumentContext context) {
        // @formatter:off
        RAGFlowResponse<List<UploadDocumentDTO>> response = this.tryUploadDocuments(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<List<UploadDocumentDTO>>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            MessageConstants.UPLOAD_DOCUMENTS_FAILED
        );
    }

    @Override
    public UpdateDocumentDTO updateDocument(UpdateDocumentContext context) {
        // @formatter:off
        RAGFlowResponse<UpdateDocumentDTO> response = this.tryUpdateDocument(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<UpdateDocumentDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            MessageConstants.UPDATE_DOCUMENT_FAILED
        );
    }

    @Override
    public DownloadHandle downloadDocument(DownloadDocumentContext context) {
        return this.tryDownloadDocument(
            context,
            Mono::block
        );
    }

    @Override
    public ListDocumentDTO listDocuments(ListDocumentContext context) {
        // @formatter:off
        RAGFlowResponse<ListDocumentDTO> response = this.tryListDocuments(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<ListDocumentDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            MessageConstants.LIST_DOCUMENTS_FAILED
        );
    }

    @Override
    public Optional<DeleteDocumentDTO> deleteDocuments(DeleteDocumentContext context) {
        // @formatter:off
        RAGFlowResponse<DeleteDocumentDTO> response = this.tryDeleteDocuments(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<DeleteDocumentDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return Optional.ofNullable(this.unwrap(response, () ->
            MessageConstants.DELETE_DOCUMENTS_FAILED
        ));
    }

    @Override
    public Optional<ParseDocumentDTO> parseDocuments(ParseDocumentContext context) {
        // @formatter:off
        RAGFlowResponse<ParseDocumentDTO> response = this.tryParseDocuments(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<ParseDocumentDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return Optional.ofNullable(this.unwrap(response, () ->
            MessageConstants.PARSE_DOCUMENTS_FAILED
        ));
    }

    @Override
    public Optional<StopParsingDocumentDTO> stopParsingDocuments(StopParsingDocumentContext context) {
        // @formatter:off
        RAGFlowResponse<StopParsingDocumentDTO> response = this.tryStopParsingDocuments(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<StopParsingDocumentDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return Optional.ofNullable(
            this.unwrap(response, () ->
                MessageConstants.STOP_PARSING_DOCUMENTS_FAILED
            )
        );
    }

    @Override
    public PollingParsingStatusDocumentDTO pollingParsingStatus(PollingParsingStatusDocumentContext context) {
        PollingParsingStatusDocumentQuery parsingQuery = context.query();
        parsingQuery.ensureIdNotBlank();

        ListDocumentQuery query = ListDocumentQuery.builder()
            .id(parsingQuery.id())
            .build();

        ListDocumentContext listContext = ListDocumentContext.builder()
            .deployKey(context.deployKey())
            .datasetId(context.datasetId())
            .query(query)
            .build();

        ListDocumentDTO documents = this.listDocuments(listContext);
        if (Objects.isNull(documents.documents()) || documents.documents().isEmpty()) {
            throw new RAGFlowException(MessageConstants.POLLING_PARSING_STATUS_FAILED, parsingQuery.id());
        }
        DocumentDTO sentinel = documents.documents().get(0);

        return PollingParsingStatusDocumentDTO.builder()
            .documentId(sentinel.getId())
            .status(sentinel.getRun())
            .build();
    }
}
