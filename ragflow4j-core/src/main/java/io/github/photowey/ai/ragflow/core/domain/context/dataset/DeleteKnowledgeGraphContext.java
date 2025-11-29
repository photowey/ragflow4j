package io.github.photowey.ai.ragflow.core.domain.context.dataset;

import io.github.photowey.ai.ragflow.core.domain.context.AbstractContext;
import io.github.photowey.ai.ragflow.core.domain.payload.dataset.DeleteKnowledgeGraphPayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Delete knowledge graph.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#delete-knowledge-graph">Delete knowledge graph</a>
 * @since 2025/11/29
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class DeleteKnowledgeGraphContext extends AbstractContext {

    private static final long serialVersionUID = 6109454317977451808L;

    /**
     * {@literal @}Nullable
     */
    private DeleteKnowledgeGraphPayload payload;
}
