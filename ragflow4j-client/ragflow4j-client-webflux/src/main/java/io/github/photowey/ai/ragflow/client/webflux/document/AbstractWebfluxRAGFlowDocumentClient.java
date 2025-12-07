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
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.photowey.ai.ragflow.client.webflux.AbstractWebfluxRAGFlowClient;
import io.github.photowey.ai.ragflow.client.webflux.core.builder.QueryParamBuilder;
import io.github.photowey.ai.ragflow.client.webflux.core.download.ErrorDownloadHandle;
import io.github.photowey.ai.ragflow.client.webflux.core.download.ReactiveStreamDownloadHandle;
import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.core.constant.MessageConstants;
import io.github.photowey.ai.ragflow.core.domain.context.document.DeleteDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.DownloadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.ListDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.ParseDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.StopParsingDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UpdateDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UploadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.download.DownloadHandle;
import io.github.photowey.ai.ragflow.core.domain.download.DownloadMetadata;
import io.github.photowey.ai.ragflow.core.domain.model.response.RAGFlowResponse;
import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * {@code AbstractWebfluxRAGFlowDocumentClient}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
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

    protected <T, D> D tryUploadDocuments(
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

    protected <T, D> D tryUpdateDocument(
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

    protected <D> D tryDownloadDocument(
        DownloadDocumentContext context,
        Function<Mono<DownloadHandle>, D> fx) {
        WebClient client = this.factory.createWebClient(context.deployKey(), this.getter);

        // This is the current best implementation for this synchronous requirement.
        // ?
        ClientResponse response = client.get()
            .uri(
                RAGFlowDictionary.API.DOWNLOAD_DOCUMENT.route(),
                context.datasetId(),
                context.payload().documentId()
            )
            .exchange()
            .block();

        HttpStatus status = Objects.requireNonNull(response).statusCode();
        MediaType contentType = response.headers().contentType()
            .orElse(MediaType.APPLICATION_OCTET_STREAM);

        if (!status.is2xxSuccessful()) {
            Mono<DownloadHandle> rvt = this.handleNon2xxResponse(response, status);
            return fx.apply(rvt);
        }

        /*
         * <pre>
         * {
         *     "code": 102,
         *     "message": "You do not own the dataset 7898da028a0511efbf750242ac1220005."
         * }
         * </pre>
         */
        if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
            Mono<DownloadHandle> rvt = this.handleBadRequest(response, status);
            return fx.apply(rvt);
        }

        String filename = context.payload().determineDownloadFilename();
        DownloadMetadata metadata = DownloadMetadata.builder()
            .code(RAGFlowDictionary.ErrorCode.OK.code())
            .message(RAGFlowDictionary.ErrorCode.OK.description())
            .filename(filename)
            .contentType(contentType.toString())
            .build();

        Flux<DataBuffer> bodyStream = response.body(BodyExtractors.toDataBuffers());
        Mono<DownloadHandle> handleMono =
            Mono.just(new ReactiveStreamDownloadHandle(metadata, bodyStream));

        return fx.apply(handleMono);
    }


    // ----------------------------------------------------------------

    protected <T, D> D tryListDocuments(
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

    protected <T, D> D tryDeleteDocuments(
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

    protected <T, D> D tryParseDocuments(
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

    protected <T, D> D tryStopParsingDocuments(
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

    // ----------------------------------------------------------------

    private Mono<DownloadHandle> handleNon2xxResponse(ClientResponse response, HttpStatus status) {
        MediaType contentType = response.headers().contentType()
            .orElse(MediaType.APPLICATION_OCTET_STREAM);

        if (MediaType.APPLICATION_JSON.isCompatibleWith(contentType)) {
            return this.handleBadRequest(response, status);
        }

        DownloadMetadata meta = DownloadMetadata.builder()
            .code(status.value())
            .message("HTTP " + status.value() + ": " + status.getReasonPhrase())
            .build();

        return Mono.just(new ErrorDownloadHandle(meta));
    }

    private DownloadMetadata extractBusinessError(Map<?, ?> body, HttpStatus fallbackStatus) {
        Integer code = Optional.ofNullable(body.get("code"))
            .map(Object::toString)
            .filter(s -> s.matches("-?\\d+"))
            .map(Integer::parseInt)
            .orElse(null);

        String message = Optional.ofNullable(body.get("message"))
            .map(Object::toString)
            .orElse("HTTP " + fallbackStatus.value() + " " + fallbackStatus.getReasonPhrase());

        if (Objects.nonNull(code) && code != 0) {
            return DownloadMetadata.builder()
                .code(code)
                .message(message)
                .build();
        }

        String errorMsg = (Objects.nonNull(code) && code == 0)
            ? "Unexpected JSON response with code=0. Expected binary file stream."
            : "Invalid or missing 'code' in JSON response. Expected binary file stream.";

        return DownloadMetadata.builder()
            .code(RAGFlowDictionary.ErrorCode.BAD_REQUEST.code())
            .message(errorMsg)
            .build();
    }

    private Mono<DownloadHandle> handleBadRequest(ClientResponse response, HttpStatus status) {
        return response.bodyToMono(Map.class)
            .map(body -> this.extractBusinessError(body, status))
            .map(meta -> (DownloadHandle) new ErrorDownloadHandle(meta))
            .switchIfEmpty(Mono.defer(() -> {
                DownloadMetadata meta = DownloadMetadata.builder()
                    .code(status.value())
                    .message("HTTP " + status.value() + " with empty response body")
                    .build();

                return Mono.just(new ErrorDownloadHandle(meta));
            }));
    }
}
