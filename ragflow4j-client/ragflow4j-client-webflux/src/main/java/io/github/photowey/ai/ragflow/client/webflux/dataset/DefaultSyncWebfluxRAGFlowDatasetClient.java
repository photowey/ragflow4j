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
package io.github.photowey.ai.ragflow.client.webflux.dataset;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import org.springframework.core.ParameterizedTypeReference;

import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.core.constant.MessageConstants;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.CreateDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.DeleteDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.DeleteKnowledgeGraphContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.GetKnowledgeGraphContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.ListDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.UpdateDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.CreateDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.DeleteDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.KnowledgeGraphDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.ListDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.UpdateDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.model.response.RAGFlowResponse;
import io.github.photowey.ai.ragflow.core.formatter.StringFormatter;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import reactor.core.publisher.Mono;

/**
 * {@code DefaultSyncWebfluxRAGFlowDatasetClient}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class DefaultSyncWebfluxRAGFlowDatasetClient extends AbstractWebfluxRAGFlowDatasetClient
    implements SyncWebfluxRAGFlowDatasetClient {

    public DefaultSyncWebfluxRAGFlowDatasetClient(
        RAGFlowPropertiesGetter getter,
        RAGFlowWebClientFactory factory) {
        super(getter, factory);
    }

    @Override
    public CreateDatasetDTO createDataset(@NotNull CreateDatasetContext context) {
        // @formatter:off
        RAGFlowResponse<CreateDatasetDTO> response = this.tryCreateDataset(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<CreateDatasetDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            StringFormatter.format(MessageConstants.CREATE_DATASET_FAILED, context.payload().name())
        );
    }

    @Override
    public DeleteDatasetDTO deleteDatasets(@NotNull DeleteDatasetContext context) {
        // @formatter:off
        RAGFlowResponse<DeleteDatasetDTO> response = this.tryDeleteDatasets(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<DeleteDatasetDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            StringFormatter.format(MessageConstants.DELETE_DATASET_FAILED, context.payload().documentIds())
        );
    }

    @Override
    public UpdateDatasetDTO updateDataset(@NotNull UpdateDatasetContext context) {
        // @formatter:off
        RAGFlowResponse<UpdateDatasetDTO> response = this.tryUpdateDataset(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<UpdateDatasetDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            StringFormatter.format(MessageConstants.UPDATE_DATASET_FAILED, context.datasetId())
        );
    }

    @Override
    public List<ListDatasetDTO> listDatasets(@NotNull ListDatasetContext context) {
        // @formatter:off
        RAGFlowResponse<List<ListDatasetDTO>> response = this.tryListDatasets(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<List<ListDatasetDTO>>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            MessageConstants.LIST_DATASET_FAILED
        );
    }

    // ----------------------------------------------------------------

    @Override
    public KnowledgeGraphDTO getKnowledgeGraph(GetKnowledgeGraphContext context) {
        // @formatter:off
        RAGFlowResponse<KnowledgeGraphDTO> response = this.tryGetKnowledgeGraph(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<KnowledgeGraphDTO>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            MessageConstants.GET_KNOWLEDGE_GRAPH_FAILED
        );
    }

    @Override
    public Boolean deleteKnowledgeGraph(DeleteKnowledgeGraphContext context) {
        // @formatter:off
        RAGFlowResponse<Boolean> response = this.tryDeleteKnowledgeGraph(
            context,
            () -> new ParameterizedTypeReference<RAGFlowResponse<Boolean>>() { },
            Mono::block
        );
        // @formatter:on

        return this.unwrap(response, () ->
            MessageConstants.DELETE_KNOWLEDGE_GRAPH_FAILED
        );
    }
}
