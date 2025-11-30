package io.github.photowey.ai.ragflow.client.webflux.document;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import io.github.photowey.ai.ragflow.client.webflux.AbstractWebfluxRAGFlowClientTest;
import io.github.photowey.ai.ragflow.client.webflux.core.factory.RAGFlowWebClientFactory;
import io.github.photowey.ai.ragflow.client.webflux.dataset.DefaultSyncWebfluxRAGFlowDatasetClient;
import io.github.photowey.ai.ragflow.client.webflux.dataset.WebfluxRAGFlowDatasetClient;
import io.github.photowey.ai.ragflow.core.domain.context.document.UpdateDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.context.document.UploadDocumentContext;
import io.github.photowey.ai.ragflow.core.domain.dto.dataset.CreateDatasetDTO;
import io.github.photowey.ai.ragflow.core.domain.dto.document.UploadDocumentDTO;
import io.github.photowey.ai.ragflow.core.domain.payload.document.UpdateDocumentPayload;
import io.github.photowey.ai.ragflow.core.domain.payload.document.UploadDocumentPayload;
import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;

/**
 * {@code WebfluxRAGFlowDocumentClientTest}.
 *
 * @author photowey
 * @version 1.0.0
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

        documentClient.updateDocument(updateContext);

        // ----------------------------------------------------------------

        tryDeleteDataset(dataset.id(), client);
    }
}
