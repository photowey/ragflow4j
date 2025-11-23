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

import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.photowey.ai.ragflow.client.webflux.AbstractRAGFlowClient;
import io.github.photowey.ai.ragflow.client.webflux.factory.WebClientFactory;
import io.github.photowey.ai.ragflow.core.domain.context.CreateDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.model.response.RAGFlowResponse;
import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import reactor.core.publisher.Mono;

/**
 * {@code AbstractRAGFlowDatasetClient}.
 *
 * @author weichangjun
 * @version 1.0.0
 * @since 2025/11/23
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public abstract class AbstractRAGFlowDatasetClient extends AbstractRAGFlowClient {

    public AbstractRAGFlowDatasetClient(
        RAGFlowPropertiesGetter getter,
        WebClientFactory factory) {
        super(getter, factory);
    }

    protected <T, D> D tryCreateDataset(
        CreateDatasetContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {

        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        // @formatter:off
        Mono<RAGFlowResponse<T>> mono = client.post()
            .uri(RAGFlowDictionary.API.CREATE_DATASET.route())
            .bodyValue(context.payload())
            .retrieve()
            .bodyToMono(ref.get());
        // @formatter:on

        return fx.apply(mono);
    }
}
