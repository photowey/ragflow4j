package io.github.photowey.ai.ragflow.client.webflux.document;

import org.springframework.core.ParameterizedTypeReference;

import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.core.constant.MessageConstants;
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
import io.github.photowey.ai.ragflow.core.domain.model.response.RAGFlowResponse;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import reactor.core.publisher.Mono;

/**
 * {@code DefaultSyncWebfluxRAGFlowDocumentClient}.
 *
 * @author photowey
 * @version 1.0.0
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
    public UploadDocumentDTO uploadDocuments(UploadDocumentContext context) {
        // @formatter:off
        RAGFlowResponse<UploadDocumentDTO> response = this.tryUploadDocuments(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<UploadDocumentDTO>>() { },
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
    public DownloadDocumentDTO downloadDocument(DownloadDocumentContext context) {
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
    public DeleteDocumentDTO deleteDocuments(DeleteDocumentContext context) {
        // @formatter:off
        RAGFlowResponse<DeleteDocumentDTO> response = this.tryDeleteDocuments(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<DeleteDocumentDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            MessageConstants.DELETE_DOCUMENTS_FAILED
        );
    }

    @Override
    public ParseDocumentDTO parseDocuments(ParseDocumentContext context) {
        // @formatter:off
        RAGFlowResponse<ParseDocumentDTO> response = this.tryParseDocuments(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<ParseDocumentDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            MessageConstants.PARSE_DOCUMENTS_FAILED
        );
    }

    @Override
    public StopParsingDocumentDTO stopParsingDocuments(StopParsingDocumentContext context) {
        // @formatter:off
        RAGFlowResponse<StopParsingDocumentDTO> response = this.tryStopParsingDocuments(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<StopParsingDocumentDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            MessageConstants.STOP_PARSING_DOCUMENTS_FAILED
        );
    }
}
