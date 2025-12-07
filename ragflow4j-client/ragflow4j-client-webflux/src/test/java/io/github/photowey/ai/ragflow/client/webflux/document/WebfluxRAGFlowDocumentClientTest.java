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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import io.github.photowey.ai.ragflow.client.webflux.AbstractWebfluxRAGFlowClientTest;
import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.client.webflux.dataset.DefaultSyncWebfluxRAGFlowDatasetClient;
import io.github.photowey.ai.ragflow.client.webflux.dataset.WebfluxRAGFlowDatasetClient;
import io.github.photowey.ai.ragflow.core.domain.context.document.DeleteDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.DownloadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.ListDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.ParseDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.PollingParsingStatusDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.StopParsingDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UpdateDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UploadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.download.DownloadHandle;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.CreateDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.DeleteDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.DocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.ListDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.ParseDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.PollingParsingStatusDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.StopParsingDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.UpdateDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.UploadDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.payload.document.DeleteDocumentPayload;
import io.github.photowey.ai.ragflow.core.domain.payload.document.DownloadDocumentPayload;
import io.github.photowey.ai.ragflow.core.domain.payload.document.ParseDocumentPayload;
import io.github.photowey.ai.ragflow.core.domain.payload.document.StopParsingDocumentPayload;
import io.github.photowey.ai.ragflow.core.domain.payload.document.UpdateDocumentPayload;
import io.github.photowey.ai.ragflow.core.domain.payload.document.UploadDocumentPayload;
import io.github.photowey.ai.ragflow.core.domain.query.document.ListDocumentQuery;
import io.github.photowey.ai.ragflow.core.domain.query.document.PollingParsingStatusDocumentQuery;
import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;
import io.github.photowey.ai.ragflow.core.exception.RAGFlowException;

/**
 * {@code WebfluxRAGFlowDocumentClientTest}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/30
 */
class WebfluxRAGFlowDocumentClientTest extends AbstractWebfluxRAGFlowClientTest {

    /**
     * <pre>
     * [
     *   {
     *     "chunk_method": "naive",
     *     "created_by": "35aeb668c2c511f0806ada3d97d6160a",
     *     "dataset_id": "eccfd85cce0211f0ad531a36cf64d2b8",
     *     "id": "ed1a5a58ce0211f0be071a36cf64d2b8",
     *     "location": "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf",
     *     "name": "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf",
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
     *     "run": "UNSTART",
     *     "size": 808202,
     *     "thumbnail": "thumbnail_ed1a5a58ce0211f0be071a36cf64d2b8.png",
     *     "type": "pdf"
     *   },
     *   {
     *     "chunk_method": "naive",
     *     "created_by": "35aeb668c2c511f0806ada3d97d6160a",
     *     "dataset_id": "eccfd85cce0211f0ad531a36cf64d2b8",
     *     "id": "ed2c668ace0211f0be071a36cf64d2b8",
     *     "location": "hotspot-virtual-machine-garbage-collection-tuning-guide-v2.pdf",
     *     "name": "hotspot-virtual-machine-garbage-collection-tuning-guide-v2.pdf",
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
     *     "run": "UNSTART",
     *     "size": 808202,
     *     "thumbnail": "thumbnail_ed2c668ace0211f0be071a36cf64d2b8.png",
     *     "type": "pdf"
     *   }
     * ]
     * </pre>
     */
    //@Test
    void testUploadDocuments() {
        WebfluxRAGFlowDatasetClient client = new DefaultSyncWebfluxRAGFlowDatasetClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        CreateDatasetDTO dataset = tryCreateDataset(client);

        String filename = "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";

        Resource pdf = new ClassPathResource("dev/pdf/" + filename);
        byte[] bytes = readAllBytes(pdf);

        UploadDocumentPayload payload = UploadDocumentPayload.builder()
            .documents(List.of(
                UploadDocumentPayload.Document.builder()
                    .name("00000000_11111111_hotspot-virtual-machine-garbage-collection-tuning-guide.pdf")
                    .originalName(filename)
                    .data(bytes)
                    .build(),
                UploadDocumentPayload.Document.builder()
                    .name("11111111_22222222_hotspot-virtual-machine-garbage-collection-tuning-guide.pdf")
                    .originalName("hotspot-virtual-machine-garbage-collection-tuning-guide-v2.pdf")
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
        Assertions.assertEquals(2, documents.size());

        tryDeleteDataset(dataset.id(), client);
    }

    //@Test
    void testUpdateDocument() {
        WebfluxRAGFlowDatasetClient client = new DefaultSyncWebfluxRAGFlowDatasetClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        CreateDatasetDTO dataset = tryCreateDataset(client);

        String filename = "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";

        Resource pdf = new ClassPathResource("dev/pdf/" + filename);
        byte[] bytes = readAllBytes(pdf);

        UploadDocumentPayload payload = UploadDocumentPayload.builder()
            .documents(List.of(
                UploadDocumentPayload.Document.builder()
                    .name("33333333_44444444_hotspot-virtual-machine-garbage-collection-tuning-guide.pdf")
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

        UpdateDocumentPayload updatePayload = UpdateDocumentPayload.builder()
            .name("33333333_44444444_hotspot-virtual-machine-garbage-collection-tuning-guide-v2.pdf")
            .meta(Map.of("hello", "world"))
            .chunkMethod(RAGFlowDictionary.ChunkMethod.BOOK.code())
            .build();

        UpdateDocumentContext updateContext = UpdateDocumentContext.builder()
            .deployKey(DEPLOY_KEY)
            .datasetId(dataset.id())
            .documentId(documents.get(0).getId())
            .payload(updatePayload)
            .build();

        UpdateDocumentDTO dto = documentClient.updateDocument(updateContext);
        Assertions.assertNotNull(dto);

        // ----------------------------------------------------------------

        tryDeleteDataset(dataset.id(), client);
    }

    //@Test
    void testDownloadDocument() throws IOException {
        WebfluxRAGFlowDatasetClient client = new DefaultSyncWebfluxRAGFlowDatasetClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        CreateDatasetDTO dataset = tryCreateDataset(client);

        String filename = "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";
        Resource pdf = new ClassPathResource("dev/pdf/" + filename);
        byte[] bytes = readAllBytes(pdf);

        UploadDocumentPayload payload = UploadDocumentPayload.builder()
            .documents(List.of(
                UploadDocumentPayload.Document.builder()
                    .name("55555555_66666666_hotspot-virtual-machine-garbage-collection-tuning-guide.pdf")
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

        UploadDocumentDTO sentinel = documents.get(0);

        DownloadDocumentPayload documentPayload = DownloadDocumentPayload.builder()
            .documentId(sentinel.getId())
            .filename(filename)
            .build();

        DownloadDocumentContext downloadContext = DownloadDocumentContext.builder()
            .deployKey(DEPLOY_KEY)
            .datasetId(dataset.id())
            .payload(documentPayload)
            .build();

        DownloadHandle handle = documentClient.downloadDocument(downloadContext);
        Assertions.assertNotNull(handle);
        Assertions.assertTrue(handle.determineIsOk());

        String downloadFilename = "download-hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";
        String copyPath = pdf.getFile().getAbsoluteFile().getParent() + File.separator + downloadFilename;

        //handle.getInputStream().transferTo(new FileOutputStream(copyPath));
        handle.writeTo(new FileOutputStream(copyPath));

        File downloadedFile = new File(copyPath);
        Assertions.assertTrue(
            downloadedFile.length() > 0, "Downloaded file is 0 bytes: " + copyPath);

        // ----------------------------------------------------------------

        DownloadDocumentPayload badDocumentPayload = DownloadDocumentPayload.builder()
            .documentId(sentinel.getId() + "_not_found")
            .filename(filename)
            .build();
        downloadContext.payload(badDocumentPayload);

        DownloadHandle badResult = documentClient.downloadDocument(downloadContext);
        Assertions.assertNotNull(badResult);
        Assertions.assertFalse(badResult.determineIsOk());

        // ----------------------------------------------------------------

        tryDeleteDataset(dataset.id(), client);
    }

    //@Test
    void testListDocuments() {
        String prefix = UUID.randomUUID().toString().replaceAll("-", "");
        String filename = "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";
        String documentName = prefix + "_" + "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";

        this.walk(filename, documentName, (dataset, document, client) -> {
            ListDocumentQuery query = ListDocumentQuery.builder()
                .id(document.getId())
                .build();

            ListDocumentContext listContext = ListDocumentContext.builder()
                .deployKey(DEPLOY_KEY)
                .datasetId(dataset.id())
                .query(query)
                .build();

            ListDocumentDTO listDocuments = client.listDocuments(listContext);

            Assertions.assertNotNull(listDocuments);
            Assertions.assertEquals(1, listDocuments.total());
            DocumentDTO sentinelDocument = listDocuments.documents().get(0);
            Assertions.assertEquals(filename, sentinelDocument.getName());
        });
    }

    //@Test
    void testDeleteDocuments() {
        String prefix = UUID.randomUUID().toString().replaceAll("-", "");
        String filename = "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";
        String documentName = prefix + "_" + "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";

        this.walk(filename, documentName, (dataset, document, client) -> {
            DeleteDocumentPayload deletePayload = DeleteDocumentPayload.builder()
                .documentIds(List.of(document.getId()))
                .build();

            DeleteDocumentContext deleteContext = DeleteDocumentContext.builder()
                .deployKey(DEPLOY_KEY)
                .datasetId(dataset.id())
                .payload(deletePayload)
                .build();

            Optional<DeleteDocumentDTO> deleteDocumentOpt = client.deleteDocuments(deleteContext);
            Assertions.assertTrue(deleteDocumentOpt.isEmpty());

            // ----------------------------------------------------------------

            ListDocumentQuery query = ListDocumentQuery.builder()
                .id(document.getId())
                .build();

            ListDocumentContext listContext = ListDocumentContext.builder()
                .deployKey(DEPLOY_KEY)
                .datasetId(dataset.id())
                .query(query)
                .build();

            // NOTES: You don't own the document b07d43fad33711f08f2fa2b9b5a62a89.
            Assertions.assertThrows(RAGFlowException.class, () -> {
                client.listDocuments(listContext);
            });
        });
    }

    //@Test
    void testParseDocuments() {
        String prefix = UUID.randomUUID().toString().replaceAll("-", "");
        String filename = "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";
        String documentName = prefix + "_" + "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";

        this.walk(filename, documentName, false, (dataset, document, client) -> {
            ParseDocumentPayload payload = ParseDocumentPayload.builder()
                .documentIds(List.of(document.getId()))
                .build();

            ParseDocumentContext context = ParseDocumentContext.builder()
                .deployKey(DEPLOY_KEY)
                .datasetId(dataset.id())
                .payload(payload)
                .build();
            Optional<ParseDocumentDTO> parseDocumentOpt = client.parseDocuments(context);
            Assertions.assertTrue(parseDocumentOpt.isEmpty());
        });
    }

    //@Test
    void testStopParsingDocuments() {
        String prefix = UUID.randomUUID().toString().replaceAll("-", "");
        String filename = "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";
        String documentName = prefix + "_" + "hotspot-virtual-machine-garbage-collection-tuning-guide.pdf";

        this.walk(filename, documentName, false, (dataset, document, client) -> {
            ParseDocumentPayload payload = ParseDocumentPayload.builder()
                .documentIds(List.of(document.getId()))
                .build();

            ParseDocumentContext context = ParseDocumentContext.builder()
                .deployKey(DEPLOY_KEY)
                .datasetId(dataset.id())
                .payload(payload)
                .build();
            Optional<ParseDocumentDTO> parseDocumentOpt = client.parseDocuments(context);
            Assertions.assertTrue(parseDocumentOpt.isEmpty());

            sleep(1_000L);

            StopParsingDocumentPayload stopPayload = StopParsingDocumentPayload.builder()
                .documentIds(List.of(document.getId()))
                .build();

            StopParsingDocumentContext stopContext = StopParsingDocumentContext.builder()
                .deployKey(DEPLOY_KEY)
                .datasetId(dataset.id())
                .payload(stopPayload)
                .build();

            Optional<StopParsingDocumentDTO> stopParsingDocumentOpt = client.stopParsingDocuments(stopContext);
            Assertions.assertTrue(stopParsingDocumentOpt.isEmpty());

            sleep(1_000L);

            PollingParsingStatusDocumentQuery query = PollingParsingStatusDocumentQuery.builder()
                .id(document.getId())
                .build();

            PollingParsingStatusDocumentContext pollingContext = PollingParsingStatusDocumentContext.builder()
                .deployKey(DEPLOY_KEY)
                .datasetId(dataset.id())
                .query(query)
                .build();

            PollingParsingStatusDocumentDTO dto = client.pollingParsingStatus(pollingContext);
            Assertions.assertTrue(dto.hasParsingCancelled());
        });
    }

    //@Test
    void testPollingParsingStatus() {
        WebfluxRAGFlowDocumentClient documentClient = new DefaultSyncWebfluxRAGFlowDocumentClient(
            () -> properties,
            new RAGFlowWebClientFactory()
        );

        String datasetId = "2438297ed33b11f0b4dda2b9b5a62a89";
        String documentId = "24c8f8b4d33b11f09b32a2b9b5a62a89";

        PollingParsingStatusDocumentQuery query = PollingParsingStatusDocumentQuery.builder()
            .id(documentId)
            .build();

        PollingParsingStatusDocumentContext context = PollingParsingStatusDocumentContext.builder()
            .deployKey(DEPLOY_KEY)
            .datasetId(datasetId)
            .query(query)
            .build();
        PollingParsingStatusDocumentDTO dto = documentClient.pollingParsingStatus(context);
        Assertions.assertTrue(dto.hasParsingFinished());
    }

}
