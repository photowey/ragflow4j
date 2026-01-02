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
package io.github.photowey.ai.ragflow.client.webflux.chunk;

import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.photowey.ai.ragflow.client.webflux.AbstractWebfluxRAGFlowClient;
import io.github.photowey.ai.ragflow.client.webflux.core.builder.QueryParamBuilder;
import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.core.domain.context.chunk.ListChunkContext;
import io.github.photowey.ai.ragflow.core.domain.context.chunk.RetrieveChunkContext;
import io.github.photowey.ai.ragflow.core.domain.model.response.RAGFlowResponse;
import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import reactor.core.publisher.Mono;

/**
 * {@code AbstractWebfluxRAGFlowChunkClient}.
 *
 * @author photowey
 * @version 2025.0.22.1.1
 * @since 2026/01/02
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public abstract class AbstractWebfluxRAGFlowChunkClient extends AbstractWebfluxRAGFlowClient {

    public AbstractWebfluxRAGFlowChunkClient(
        RAGFlowPropertiesGetter getter,
        RAGFlowWebClientFactory factory) {
        super(getter, factory);
    }

    public <T, D> D tryRetireveChunks(
        RetrieveChunkContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {
        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.RETRIEVE_CHUNKS)
            .uri(RAGFlowDictionary.API.RETRIEVE_CHUNKS.route())
            .bodyValue(context.payload())
            .retrieve()
            .bodyToMono(ref.get());

        return fx.apply(mono);
    }

    public <T, D> D tryListChunks(
        ListChunkContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {
        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);
        MultiValueMap<String, String> queryParams = QueryParamBuilder.toQueryParams(context.toQuery());

        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.LIST_CHUNKS)
            .uri(builder -> builder
                .path(RAGFlowDictionary.API.LIST_CHUNKS.route())
                .queryParams(queryParams)
                .build(context.datasetId(), context.documentId())
            )
            .retrieve()
            .bodyToMono(ref.get());

        return fx.apply(mono);
    }
}
