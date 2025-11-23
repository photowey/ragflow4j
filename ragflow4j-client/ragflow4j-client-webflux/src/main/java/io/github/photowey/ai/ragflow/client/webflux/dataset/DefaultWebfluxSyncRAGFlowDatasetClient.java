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

import org.springframework.core.ParameterizedTypeReference;

import io.github.photowey.ai.ragflow.client.webflux.factory.WebClientFactory;
import io.github.photowey.ai.ragflow.core.constant.MessageConstants;
import io.github.photowey.ai.ragflow.core.domain.context.CreateDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.CreateDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.model.response.RAGFlowResponse;
import io.github.photowey.ai.ragflow.core.formatter.StringFormatter;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import reactor.core.publisher.Mono;

/**
 * {@code DefaultWebfluxSyncRAGFlowDatasetClient}.
 *
 * @author weichangjun
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class DefaultWebfluxSyncRAGFlowDatasetClient extends AbstractRAGFlowDatasetClient
    implements WebfluxSyncRAGFlowDatasetClient {

    public DefaultWebfluxSyncRAGFlowDatasetClient(
        RAGFlowPropertiesGetter getter,
        WebClientFactory factory) {
        super(getter, factory);
    }

    @Override
    public CreateDatasetDTO createDataset(CreateDatasetContext context) {
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

}
