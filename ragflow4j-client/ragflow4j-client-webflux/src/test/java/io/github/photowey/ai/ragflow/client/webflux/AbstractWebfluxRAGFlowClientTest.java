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
package io.github.photowey.ai.ragflow.client.webflux;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.photowey.ai.ragflow.client.webflux.dataset.WebfluxRAGFlowDatasetClient;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.CreateDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.DeleteDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.CreateDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.payload.dataset.CreateDatasetPayload;
import io.github.photowey.ai.ragflow.core.domain.payload.dataset.DeleteDatasetPayload;
import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;
import io.github.photowey.ai.ragflow.core.property.RAGFlowProperties;

/**
 * {@code AbstractWebfluxRAGFlowClientTest}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/30
 */
public abstract class AbstractWebfluxRAGFlowClientTest {

    protected static RAGFlowProperties properties;

    protected static ObjectMapper objectMapper;

    protected static final String DEPLOY_KEY = "io.github.photowey.ai.ragflow.local.deploy";

    @BeforeAll
    static void init() {
        String address = System.getenv("RAGFLOW_ADDRESS");
        String apiKey = System.getenv("RAGFLOW_API_KEY");

        properties = RAGFlowProperties.builder()
            .client(new RAGFlowProperties.Client())
            .servers(new HashMap<>(
                Map.of(
                    "global",
                    RAGFlowProperties.Server.builder()
                        .address(address)
                        .apiKey(apiKey)
                        .timeout(30_000)
                        .build(),
                    DEPLOY_KEY,
                    RAGFlowProperties.Server.builder()
                        .address(address)
                        .apiKey(apiKey)
                        .timeout(30_000)
                        .codec(
                            RAGFlowProperties.Codec.builder()
                                .maxInMemorySize(256 * 1024)
                                .build()
                        )
                        .build()
                )
            ))
            .datasets(new HashMap<>())
            .build();

        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected static CreateDatasetDTO tryCreateDataset(WebfluxRAGFlowDatasetClient client) {
        String name = "Hello-FRAGFlow-Dataset-" + System.currentTimeMillis();
        CreateDatasetPayload payload = CreateDatasetPayload.builder()
            .name(name)
            .description("Hello RAGFlow!")
            .permission("team")
            .chunkMethod(RAGFlowDictionary.ChunkMethod.GENERAL.code())
            .build();

        CreateDatasetContext context = CreateDatasetContext.builder()
            .deployKey(DEPLOY_KEY)
            .payload(payload)
            .build();

        CreateDatasetDTO dataset = client.createDataset(context);
        Assertions.assertNotNull(dataset);
        Assertions.assertNotNull(dataset.id());
        return dataset;
    }

    // ----------------------------------------------------------------

    protected static void tryDeleteDataset(String datasetId, WebfluxRAGFlowDatasetClient client) {
        DeleteDatasetPayload deletePayload = DeleteDatasetPayload.builder()
            .documentIds(List.of(datasetId))
            .build();

        DeleteDatasetContext deleteContext = DeleteDatasetContext.builder()
            .deployKey(DEPLOY_KEY)
            .payload(deletePayload)
            .build();

        client.deleteDatasets(deleteContext);
    }

    // ----------------------------------------------------------------

    protected static String toJSONString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------------------------

    protected static byte[] readAllBytes(Resource resource) {
        try {
            if (Objects.isNull(resource)) {
                throw new NullPointerException("resource must not be bull");
            }

            if (!resource.exists()) {
                throw new RuntimeException("resource must be exists");
            }

            return resource.getInputStream().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
