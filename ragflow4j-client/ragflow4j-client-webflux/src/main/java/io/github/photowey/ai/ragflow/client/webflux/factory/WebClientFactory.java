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
package io.github.photowey.ai.ragflow.client.webflux.factory;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.photowey.ai.ragflow.core.constant.MessageConstants;
import io.github.photowey.ai.ragflow.core.constant.RAGFlowConstants;
import io.github.photowey.ai.ragflow.core.exception.RAGFlowException;
import io.github.photowey.ai.ragflow.core.property.RAGFlowProperties;
import io.github.photowey.ai.ragflow.core.property.RAGFlowPropertiesGetter;

import reactor.netty.http.client.HttpClient;

/**
 * {@code WebClientFactory}.
 *
 * @author weichangjun
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
public class WebClientFactory {

    private final Map<String, WebClient> clientCache = new ConcurrentHashMap<>();

    // ----------------------------------------------------------------

    public WebClient createFormdataWebClient(String deployKey, RAGFlowPropertiesGetter getter) {
        return this.clientCache.computeIfAbsent(deployKey,
            (key) -> this.createFormdataWebClient(key, getter, WebClientFactory::nothing)
        );
    }

    public WebClient createFormdataWebClient(
        String deployKey,
        RAGFlowPropertiesGetter getter,
        Consumer<HttpClient> clientFx) {
        return this.tryCreateWebClient(deployKey, getter, clientFx, (builder) -> {
            builder.defaultHeader(RAGFlowConstants.Header.CONTENT_TYPE, RAGFlowConstants.Header.MULTIPART_FORM_DATA);
        });
    }

    // ----------------------------------------------------------------

    public WebClient createWebClient(String deployKey, RAGFlowPropertiesGetter getter) {
        return this.tryCreateWebClient(deployKey, getter, WebClientFactory::nothing, (builder) -> {
            builder.defaultHeader(RAGFlowConstants.Header.CONTENT_TYPE, RAGFlowConstants.Header.APPLICATION_JSON);
        });
    }

    public WebClient createWebClient(String deployKey, RAGFlowPropertiesGetter getter, Consumer<HttpClient> clientFx) {
        return this.tryCreateWebClient(deployKey, getter, clientFx, (builder) -> {
            builder.defaultHeader(RAGFlowConstants.Header.CONTENT_TYPE, RAGFlowConstants.Header.APPLICATION_JSON);
        });
    }

    // ----------------------------------------------------------------

    private WebClient tryCreateWebClient(
        String deployKey,
        RAGFlowPropertiesGetter getter,
        Consumer<HttpClient> clientFx,
        Consumer<WebClient.Builder> builderFx) {
        RAGFlowProperties.Server server = getter.get().tryAcquireServer(deployKey).orElseThrow(() -> {
            return new RAGFlowException(MessageConstants.RAGFLOW_DEPLOYMENT_KEY_INVALID, deployKey);
        });

        HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofMillis(server.timeout(5000)));

        clientFx.accept(httpClient);

        WebClient.Builder builder = WebClient.builder()
            .baseUrl(server.address())
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader(
                RAGFlowConstants.Header.AUTHORIZATION, RAGFlowConstants.Header.BEARER + " " + server.apiKey()
            );

        builderFx.accept(builder);

        return builder.build();
    }

    // ----------------------------------------------------------------

    public static <T> void nothing(T t) {

    }
}
