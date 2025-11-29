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
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.photowey.ai.ragflow.client.webflux.AbstractRAGFlowClient;
import io.github.photowey.ai.ragflow.client.webflux.core.builder.QueryParamBuilder;
import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.CreateDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.DeleteDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.DeleteKnowledgeGraphContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.GetKnowledgeGraphContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.ListDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.UpdateDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.model.response.RAGFlowResponse;
import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import reactor.core.publisher.Mono;

/**
 * {@code AbstractRAGFlowDatasetClient}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public abstract class AbstractRAGFlowDatasetClient extends AbstractRAGFlowClient {

    public AbstractRAGFlowDatasetClient(
        RAGFlowPropertiesGetter getter,
        RAGFlowWebClientFactory factory) {
        super(getter, factory);
    }

    protected <T, D> D tryCreateDataset(
        CreateDatasetContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {

        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        // @formatter:off
        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.CREATE_DATASET)
            .uri(RAGFlowDictionary.API.CREATE_DATASET.route())
            .bodyValue(context.payload())
            .retrieve()
            .bodyToMono(ref.get());
        // @formatter:on

        return fx.apply(mono);
    }

    protected <T, D> D tryDeleteDatasets(
        DeleteDatasetContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {

        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        // @formatter:off
        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.DELETE_DATASETS)
            .uri(RAGFlowDictionary.API.DELETE_DATASETS.route())
            .bodyValue(context.payload())
            .retrieve()
            .bodyToMono(ref.get());
        // @formatter:on

        return fx.apply(mono);
    }

    protected <T, D> D tryUpdateDataset(
        UpdateDatasetContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {

        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        // @formatter:off
        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.UPDATE_DATASET)
            .uri(RAGFlowDictionary.API.UPDATE_DATASET.route(), context.datasetId())
            .bodyValue(context.payload())
            .retrieve()
            .bodyToMono(ref.get());
        // @formatter:on

        return fx.apply(mono);
    }

    protected <T, D> D tryListDatasets(
        ListDatasetContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {

        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);
        MultiValueMap<String, String> queryParams = QueryParamBuilder.toQueryParams(context.query());

        // @formatter:off
        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.LIST_DATASETS)
            .uri(builder -> builder
                .path(RAGFlowDictionary.API.LIST_DATASETS.route())
                .queryParams(queryParams)
                .build()
            )
            .retrieve()
            .bodyToMono(ref.get());
        // @formatter:on

        return fx.apply(mono);
    }

    // ----------------------------------------------------------------

    protected <T, D> D tryGetKnowledgeGraph(
        GetKnowledgeGraphContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {

        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        // @formatter:off
        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.GET_KNOWLEDGE_GRAPH)
            .uri(RAGFlowDictionary.API.GET_KNOWLEDGE_GRAPH.route(), context.datasetId())
            .retrieve()
            .bodyToMono(ref.get());
        // @formatter:on

        return fx.apply(mono);
    }

    protected <T, D> D tryDeleteKnowledgeGraph(
        DeleteKnowledgeGraphContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {

        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        // @formatter:off
        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.DELETE_KNOWLEDGE_GRAPH)
            .uri(RAGFlowDictionary.API.DELETE_KNOWLEDGE_GRAPH.route(), context.datasetId())
            .retrieve()
            .bodyToMono(ref.get());
        // @formatter:on

        return fx.apply(mono);
    }

    // ----------------------------------------------------------------

    private WebClient.RequestBodyUriSpec create(WebClient client, RAGFlowDictionary.API api) {
        HttpMethod httpMethod = HttpMethod.valueOf(api.method());
        return client.method(httpMethod);
    }
}
