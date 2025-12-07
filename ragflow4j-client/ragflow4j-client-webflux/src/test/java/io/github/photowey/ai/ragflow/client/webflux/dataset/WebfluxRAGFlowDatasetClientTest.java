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

import org.junit.jupiter.api.Assertions;

import io.github.photowey.ai.ragflow.client.webflux.AbstractWebfluxRAGFlowClientTest;
import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.CreateDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.DeleteDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.DeleteKnowledgeGraphContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.GetKnowledgeGraphContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.ListDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.context.dataset.UpdateDatasetContext;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.CreateDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.KnowledgeGraphDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.ListDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.payload.dataset.CreateDatasetPayload;
import io.github.photowey.ai.ragflow.core.domain.payload.dataset.DeleteDatasetPayload;
import io.github.photowey.ai.ragflow.core.domain.payload.dataset.UpdateDatasetPayload;
import io.github.photowey.ai.ragflow.core.domain.query.dataset.ListDatasetQuery;
import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code WebfluxRAGFlowDatasetClientTest}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
@Slf4j
class WebfluxRAGFlowDatasetClientTest extends AbstractWebfluxRAGFlowClientTest {

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
        WebfluxRAGFlowDatasetClient client = new DefaultSyncWebfluxRAGFlowDatasetClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        CreateDatasetPayload payload = CreateDatasetPayload.builder()
            .name("Hello-FRAGFlow-Dataset3")
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
    }

    // ----------------------------------------------------------------

    //@Test
    void testDeleteDatasets() {
        WebfluxRAGFlowDatasetClient client = new DefaultSyncWebfluxRAGFlowDatasetClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        DeleteDatasetPayload payload = DeleteDatasetPayload.builder()
            .documentIds(List.of(
                "a6b0cc76c86411f0acb406a4f1a8fa0e",
                "28da6ec8c86511f0af2206a4f1a8fa0e"
            ))
            .build();

        DeleteDatasetContext context = DeleteDatasetContext.builder()
            .deployKey(DEPLOY_KEY)
            .payload(payload)
            .build();

        client.deleteDatasets(context);
    }

    //@Test
    void testDeleteDatasets_v2() {
        WebfluxRAGFlowDatasetClient client = new DefaultSyncWebfluxRAGFlowDatasetClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        CreateDatasetDTO dataset = tryCreateDataset(client);

        // ----------------------------------------------------------------

        tryDeleteDataset(dataset.id(), client);
    }

    // ----------------------------------------------------------------

    //@Test
    void testUpdateDataset() {
        WebfluxRAGFlowDatasetClient client = new DefaultSyncWebfluxRAGFlowDatasetClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        CreateDatasetDTO dataset = tryCreateDataset(client);

        // ----------------------------------------------------------------

        UpdateDatasetPayload updatePayload = UpdateDatasetPayload.builder()
            .name(dataset.getName() + "-Updated")
            .description("Hello RAGFlow!-Updated")
            .permission("me")
            .chunkMethod(RAGFlowDictionary.ChunkMethod.BOOK.code())
            .pagerank(5)
            .build();

        UpdateDatasetContext updateContext = UpdateDatasetContext.builder()
            .deployKey(DEPLOY_KEY)
            .datasetId(dataset.id())
            .payload(updatePayload)
            .build();

        client.updateDataset(updateContext);

        // ----------------------------------------------------------------

        tryDeleteDataset(dataset.id(), client);
    }

    // ----------------------------------------------------------------

    /**
     * <pre>
     * [
     *   {
     *     "avatar": null,
     *     "chunk_count": 0,
     *     "chunk_method": "naive",
     *     "create_date": "Sun, 30 Nov 2025 22:55:57 GMT",
     *     "create_time": 1764514557206,
     *     "created_by": "35aeb668c2c511f0806ada3d97d6160a",
     *     "description": "Hello RAGFlow!",
     *     "document_count": 0,
     *     "embedding_model": "text-embedding-v4@Tongyi-Qianwen",
     *     "id": "ad55fe32cdfc11f0ad301a36cf64d2b8",
     *     "language": "English",
     *     "name": "Hello-FRAGFlow-Dataset-1764514555798",
     *     "pagerank": 0,
     *     "parser_config": {
     *       "auto_keywords": 0,
     *       "auto_questions": 0,
     *       "chunk_token_num": 512,
     *       "delimiter": "\n",
     *       "html4excel": false,
     *       "layout_recognize": "DeepDOC",
     *       "tag_kb_ids": null,
     *       "task_page_size": null,
     *       "raptor": {
     *         "max_cluster": 64,
     *         "max_token": 256,
     *         "prompt": "Please summarize the following paragraphs. Be careful with the numbers, do not make things up. Paragraphs as following:\n      {cluster_content}\nThe above is the content you need to summarize.",
     *         "random_seed": 0,
     *         "threshold": 0.1,
     *         "use_raptor": true
     *       },
     *       "graphrag": {
     *         "entity_types": [
     *           "organization",
     *           "person",
     *           "geo",
     *           "event",
     *           "category"
     *         ],
     *         "method": "light",
     *         "use_graphrag": true
     *       }
     *     },
     *     "permission": "team",
     *     "similarity_threshold": 0.2,
     *     "status": "1",
     *     "tenant_id": "35aeb668c2c511f0806ada3d97d6160a",
     *     "token_num": 0,
     *     "update_date": "Sun, 30 Nov 2025 22:55:57 GMT",
     *     "update_time": 1764514557206,
     *     "vector_similarity_weight": 0.3
     *   }
     * ]
     * </pre>
     */
    //@Test
    void testListDatasets() {
        WebfluxRAGFlowDatasetClient client = new DefaultSyncWebfluxRAGFlowDatasetClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        CreateDatasetDTO dataset = tryCreateDataset(client);

        // ----------------------------------------------------------------

        ListDatasetQuery query = ListDatasetQuery.builder()
            .page(1)
            .pageSize(10)
            .id(dataset.id())
            .build();

        ListDatasetContext listContext = ListDatasetContext.builder()
            .deployKey(DEPLOY_KEY)
            .query(query)
            .build();

        List<ListDatasetDTO> datasets = client.listDatasets(listContext);
        Assertions.assertEquals(1, datasets.size());

        // ----------------------------------------------------------------

        tryDeleteDataset(dataset.id(), client);
    }

    //@Test
    void testGetKnowledgeGraph() {
        WebfluxRAGFlowDatasetClient client = new DefaultSyncWebfluxRAGFlowDatasetClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        CreateDatasetDTO dataset = tryCreateDataset(client);

        // ----------------------------------------------------------------

        GetKnowledgeGraphContext graphContext = GetKnowledgeGraphContext.builder()
            .deployKey(DEPLOY_KEY)
            .datasetId(dataset.getId())
            .build();

        KnowledgeGraphDTO knowledgeGraph = client.getKnowledgeGraph(graphContext);
        Assertions.assertNotNull(knowledgeGraph);

        // ----------------------------------------------------------------

        tryDeleteDataset(dataset.id(), client);
    }

    //@Test
    void testDeleteKnowledgeGraph() {
        WebfluxRAGFlowDatasetClient client = new DefaultSyncWebfluxRAGFlowDatasetClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        CreateDatasetDTO dataset = tryCreateDataset(client);

        // ----------------------------------------------------------------

        DeleteKnowledgeGraphContext graphContext = DeleteKnowledgeGraphContext.builder()
            .deployKey(DEPLOY_KEY)
            .datasetId(dataset.getId())
            .build();

        Boolean deleted = client.deleteKnowledgeGraph(graphContext);
        Assertions.assertTrue(deleted);

        // ----------------------------------------------------------------

        tryDeleteDataset(dataset.getId(), client);
    }

    // ----------------------------------------------------------------


}
