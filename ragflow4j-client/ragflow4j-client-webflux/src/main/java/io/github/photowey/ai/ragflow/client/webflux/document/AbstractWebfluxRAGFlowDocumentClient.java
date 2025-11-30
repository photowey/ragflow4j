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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.photowey.ai.ragflow.client.webflux.AbstractWebfluxRAGFlowClient;
import io.github.photowey.ai.ragflow.client.webflux.core.builder.QueryParamBuilder;
import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.core.constant.MessageConstants;
import io.github.photowey.ai.ragflow.core.domain.context.document.DeleteDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.DownloadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.ListDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.ParseDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.StopParsingDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UpdateDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UploadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.dto.document.DownloadDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.model.response.RAGFlowResponse;
import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import reactor.core.publisher.Mono;

/**
 * {@code AbstractWebfluxRAGFlowDocumentClient}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/11/30
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public abstract class AbstractWebfluxRAGFlowDocumentClient extends AbstractWebfluxRAGFlowClient {

    public AbstractWebfluxRAGFlowDocumentClient(
        RAGFlowPropertiesGetter getter,
        RAGFlowWebClientFactory factory) {
        super(getter, factory);
    }

    // ----------------------------------------------------------------

    public <T, D> D tryUploadDocuments(
        UploadDocumentContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {
        WebClient client = this.factory.createFormdataWebClient(context.deployKey(), this.getter);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        List<Resource> resources = this.tryConvertDocuments(context);
        for (Resource resource : resources) {
            builder.part("file", resource);
        }

        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.UPLOAD_DOCUMENTS)
            .uri(RAGFlowDictionary.API.UPLOAD_DOCUMENTS.route(), context.datasetId())
            .body(BodyInserters.fromMultipartData(builder.build()))
            .retrieve()
            .bodyToMono(ref.get());

        return fx.apply(mono);
    }

    // ----------------------------------------------------------------

    public <T, D> D tryUpdateDocument(
        UpdateDocumentContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {
        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.UPDATE_DOCUMENT)
            .uri(RAGFlowDictionary.API.UPDATE_DOCUMENT.route(), context.datasetId(), context.documentId())
            .bodyValue(context.payload())
            .retrieve()
            .bodyToMono(ref.get());

        return fx.apply(mono);
    }

    // ----------------------------------------------------------------

    public <D> D tryDownloadDocument(
        DownloadDocumentContext context,
        Function<Mono<DownloadDocumentDTO>, D> fx) {
        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        Mono<DownloadDocumentDTO> mono = this.create(client, RAGFlowDictionary.API.DOWNLOAD_DOCUMENT)
            .uri(RAGFlowDictionary.API.DOWNLOAD_DOCUMENT.route(), context.datasetId(), context.payload().documentId())
            .exchangeToMono(response -> {
                MediaType contentType = response.headers().contentType()
                    .orElse(MediaType.APPLICATION_OCTET_STREAM);

                if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
                    return response.bodyToMono(Map.class)
                        .map(body -> {
                            Integer code = Optional.ofNullable(body.get("code"))
                                .map(Object::toString)
                                .filter(s -> s.matches("\\d+"))
                                .map(Integer::parseInt)
                                .orElse(500);
                            String message = Optional.ofNullable(body.get("message"))
                                .map(Object::toString)
                                .orElse("Unknown error from RAGFlow");

                            return DownloadDocumentDTO.builder()
                                .code(code)
                                .message(message)
                                .data(new byte[0])
                                .build();
                        });
                }

                return response.bodyToMono(byte[].class)
                    .map(bytes -> DownloadDocumentDTO.builder()
                        .code(0)
                        .message("OK")
                        .filename(context.payload().determineDownloadFilename())
                        .data(bytes)
                        .build());
            });

        return fx.apply(mono);
    }

    // ----------------------------------------------------------------

    public <T, D> D tryListDocuments(
        ListDocumentContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {
        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);
        MultiValueMap<String, String> queryParams = QueryParamBuilder.toQueryParams(context.query());

        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.LIST_DOCUMENTS)
            .uri(builder -> builder
                .path(RAGFlowDictionary.API.LIST_DOCUMENTS.route())
                .queryParams(queryParams)
                .build(context.datasetId())
            )
            .retrieve()
            .bodyToMono(ref.get());

        return fx.apply(mono);
    }

    // ----------------------------------------------------------------

    public <T, D> D tryDeleteDocuments(
        DeleteDocumentContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {
        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.DELETE_DOCUMENTS)
            .uri(RAGFlowDictionary.API.DELETE_DOCUMENTS.route(), context.datasetId())
            .bodyValue(context.payload())
            .retrieve()
            .bodyToMono(ref.get());

        return fx.apply(mono);
    }

    // ----------------------------------------------------------------

    public <T, D> D tryParseDocuments(
        ParseDocumentContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {
        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.PARSE_DOCUMENTS)
            .uri(RAGFlowDictionary.API.PARSE_DOCUMENTS.route(), context.datasetId())
            .bodyValue(context.payload())
            .retrieve()
            .bodyToMono(ref.get());

        return fx.apply(mono);
    }

    // ----------------------------------------------------------------

    public <T, D> D tryStopParsingDocuments(
        StopParsingDocumentContext context,
        Supplier<ParameterizedTypeReference<RAGFlowResponse<T>>> ref,
        Function<Mono<RAGFlowResponse<T>>, D> fx) {
        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        Mono<RAGFlowResponse<T>> mono = this.create(client, RAGFlowDictionary.API.STOP_PARSING_DOCUMENTS)
            .uri(RAGFlowDictionary.API.STOP_PARSING_DOCUMENTS.route(), context.datasetId())
            .bodyValue(context.payload())
            .retrieve()
            .bodyToMono(ref.get());

        return fx.apply(mono);
    }

    // ----------------------------------------------------------------

    protected List<Resource> tryConvertDocuments(UploadDocumentContext context) {
        if (Objects.isNull(context.payload().documents())
            || context.payload().documents().isEmpty()) {
            throw new RuntimeException(MessageConstants.UPLOAD_DOCUMENT_DOCUMENT_LIST_EMPTY);
        }

        return context.payload().documents().stream().map(it -> {
            return new ByteArrayResource(it.data()) {
                @Override
                public String getFilename() {
                    return it.originalName();
                }
            };
        }).collect(Collectors.toList());
    }
}
