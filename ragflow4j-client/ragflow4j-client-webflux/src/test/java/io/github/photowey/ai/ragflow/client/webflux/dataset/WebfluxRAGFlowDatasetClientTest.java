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

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.photowey.ai.ragflow.client.webflux.factory.WebClientFactory;
import io.github.photowey.ai.ragflow.core.domain.context.CreateDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.CreateDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.payload.dataset.CreateDatasetPayload;
import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;
import io.github.photowey.ai.ragflow.core.property.RAGFlowProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code WebfluxRAGFlowDatasetClientTest}.
 *
 * @author weichangjun
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
@Slf4j
class WebfluxRAGFlowDatasetClientTest {

    private static RAGFlowProperties properties;

    private static ObjectMapper objectMapper;

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
                    "io.github.photowey.ai.ragflow.local.deploy",
                    RAGFlowProperties.Server.builder()
                        .address(address)
                        .apiKey(apiKey)
                        .timeout(30_000)
                        .build()
                )
            ))
            .datasets(new HashMap<>())
            .build();

        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * <pre>
     * {
     *   "avatar": null,
     *   "chunk_count": 0,
     *   "chunk_method": "naive",
     *   "create_date": "Sun, 23 Nov 2025 20:08:45 GMT",
     *   "create_time": 1763899725117,
     *   "created_by": "35aeb668c2c511f0806ada3d97d6160a",
     *   "description": "Hello RAGFlow!",
     *   "document_count": 0,
     *   "embedding_model": "text-embedding-v4@Tongyi-Qianwen",
     *   "id": "28da6ec8c86511f0af2206a4f1a8fa0e",
     *   "language": "English",
     *   "name": "Hello-FRAGFlow-Dataset3",
     *   "pagerank": 0,
     *   "parser_config": {
     *     "auto_keywords": 0,
     *     "auto_questions": 0,
     *     "chunk_token_num": 512,
     *     "delimiter": "\n",
     *     "html4excel": false,
     *     "layout_recognize": "DeepDOC",
     *     "tag_kb_ids": null,
     *     "task_page_size": null,
     *     "raptor": {
     *       "max_cluster": 64,
     *       "max_token": 256,
     *       "prompt": "Please summarize the following paragraphs. Be careful with the numbers, do not make things up. Paragraphs as following:\n      {cluster_content}\nThe above is the content you need to summarize.",
     *       "random_seed": 0,
     *       "threshold": 0.1,
     *       "use_raptor": true
     *     },
     *     "graphrag": {
     *       "entity_types": [
     *         "organization",
     *         "person",
     *         "geo",
     *         "event",
     *         "category"
     *       ],
     *       "method": "light",
     *       "use_graphrag": true
     *     }
     *   },
     *   "permission": "team",
     *   "similarity_threshold": 0.2,
     *   "status": "1",
     *   "tenant_id": "35aeb668c2c511f0806ada3d97d6160a",
     *   "token_num": 0,
     *   "update_date": "Sun, 23 Nov 2025 20:08:45 GMT",
     *   "update_time": 1763899725117,
     *   "vector_similarity_weight": 0.3
     * }
     * </pre>
     */
    //@Test
    void testCreateDataset() {
        WebfluxRAGFlowDatasetClient client = new DefaultWebfluxSyncRAGFlowDatasetClient(
            () -> properties,
            new WebClientFactory()
        );

        CreateDatasetPayload payload = CreateDatasetPayload.builder()
            .name("Hello-FRAGFlow-Dataset3")
            .description("Hello RAGFlow!")
            .permission("team")
            .chunkMethod(RAGFlowDictionary.ChunkMethod.GENERAL.code())
            .build();

        CreateDatasetContext context = CreateDatasetContext.builder()
            .deployKey("io.github.photowey.ai.ragflow.local.deploy")
            .payload(payload)
            .build();

        CreateDatasetDTO dataset = client.createDataset(context);

        log.info("the create dataset response data is:{}", toJSONString(dataset));

        Assertions.assertNotNull(dataset);
        Assertions.assertNotNull(dataset.id());
    }

    private static String toJSONString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
