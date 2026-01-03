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

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import io.github.photowey.ai.ragflow.client.webflux.AbstractWebfluxRAGFlowClientTest;
import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.client.webflux.dataset.DefaultSyncWebfluxRAGFlowDatasetClient;
import io.github.photowey.ai.ragflow.client.webflux.dataset.WebfluxRAGFlowDatasetClient;
import io.github.photowey.ai.ragflow.client.webflux.document.DefaultSyncWebfluxRAGFlowDocumentClient;
import io.github.photowey.ai.ragflow.client.webflux.document.WebfluxRAGFlowDocumentClient;
import io.github.photowey.ai.ragflow.core.domain.context.chunk.ListChunkContext;
import io.github.photowey.ai.ragflow.core.domain.context.chunk.RetrieveChunkContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UploadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.dto.chunk.ListChunkDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.chunk.RetrieveChunkDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.CreateDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.UploadDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.payload.chunk.RetrieveChunkPayload;
import io.github.photowey.ai.ragflow.core.domain.payload.document.UploadDocumentPayload;
import io.github.photowey.ai.ragflow.core.domain.query.chunk.ListChunkQuery;

import lombok.extern.slf4j.Slf4j;

/**
 * {@code WebfluxRAGFlowChunkClientTest}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2026/01/03
 */
@Slf4j
class WebfluxRAGFlowChunkClientTest extends AbstractWebfluxRAGFlowClientTest {

    //@Test
    void testRetrieveChunks_upload() {
        WebfluxRAGFlowDatasetClient client = new DefaultSyncWebfluxRAGFlowDatasetClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        CreateDatasetDTO dataset = tryCreateDataset(client);

        final String filename = "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";

        Resource pdf = new ClassPathResource("dev/pdf/" + filename);
        byte[] bytes = readAllBytes(pdf);

        UploadDocumentPayload payload = UploadDocumentPayload.builder()
            .documents(List.of(
                UploadDocumentPayload.Document.builder()
                    .name("11111111_22222222_hotspot-virtual-machine-garbage-collection-tuning-guide.pdf")
                    .originalName(filename)
                    .data(bytes)
                    .build()
            ))
            .build();

        UploadDocumentContext context = UploadDocumentContext.builder()
            .deployKey(DEPLOY_KEY)
            .datasetId(dataset.id())
            .payload(payload)
            .build();

        WebfluxRAGFlowDocumentClient documentClient = new DefaultSyncWebfluxRAGFlowDocumentClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        List<UploadDocumentDTO> documents = documentClient.uploadDocuments(context);
        Assertions.assertNotNull(documents);
        Assertions.assertEquals(1, documents.size());
    }

    //@Test
    void testRetrieveChunks_retrieve() {
        // http://192.168.1.2/chunk/parsed/chunks?id=39ccb108e8b011f0bdc13ec6685d1e45&doc_id=3a1eb0d4e8b011f0bdc13ec6685d1e45

        final String datasetId = "39ccb108e8b011f0bdc13ec6685d1e45";
        final String documentId = "3a1eb0d4e8b011f0bdc13ec6685d1e45";

        RetrieveChunkPayload payload = RetrieveChunkPayload.builder()
            .question("Introduction to Garbage Collection Tuning")
            .datasetIds(List.of(datasetId))
            .documentIds(List.of(documentId))
            .page(1)
            .pageSize(5)
            .similarityThreshold(BigDecimal.valueOf(0.2))
            .vectorSimilarityWeight(BigDecimal.valueOf(0.3))
            .topK(5)
            //.rerankId
            //.keyword
            //.highlight
            //.crossLanguages
            //.metadataCondition
            //.useKg
            .build();

        RetrieveChunkContext context = RetrieveChunkContext.builder()
            .deployKey(DEPLOY_KEY)
            .datasetId(datasetId)
            // ----------------------------------------------------------------
            .payload(payload)
            .build();
        WebfluxRAGFlowChunkClient chunkClient = new DefaultSyncWebfluxRAGFlowChunkClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        RetrieveChunkDTO rvt = chunkClient.retrieveChunks(context);
        Assertions.assertNotNull(rvt);
        Assertions.assertFalse(rvt.chunks().isEmpty());
    }

    //@Test
    void testListChunks() {
        // http://192.168.1.2/chunk/parsed/chunks?id=39ccb108e8b011f0bdc13ec6685d1e45&doc_id=3a1eb0d4e8b011f0bdc13ec6685d1e45

        final String datasetId = "39ccb108e8b011f0bdc13ec6685d1e45";
        final String documentId = "3a1eb0d4e8b011f0bdc13ec6685d1e45";

        ListChunkQuery query = ListChunkQuery.builder()
            .page(1)
            .pageSize(10)
            .build();

        ListChunkContext context = ListChunkContext.builder()
            .deployKey(DEPLOY_KEY)
            .datasetId(datasetId)
            // ----------------------------------------------------------------
            .documentId(documentId)
            .query(query)
            .build();
        WebfluxRAGFlowChunkClient chunkClient = new DefaultSyncWebfluxRAGFlowChunkClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        ListChunkDTO rvt = chunkClient.listChunks(context);
        Assertions.assertNotNull(rvt);
        Assertions.assertFalse(rvt.chunks().isEmpty());
    }
}
