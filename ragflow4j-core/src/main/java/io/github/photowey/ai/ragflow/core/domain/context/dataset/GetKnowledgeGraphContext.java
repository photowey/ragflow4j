package io.github.photowey.ai.ragflow.core.domain.context.dataset;

import io.github.photowey.ai.ragflow.core.domain.context.AbstractContext;
import io.github.photowey.ai.ragflow.core.domain.query.dataset.GetKnowledgeGraphQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Get knowledge graph.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#get-knowledge-graph">Get knowledge graph</a>
 * @since 2025/11/29
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class GetKnowledgeGraphContext extends AbstractContext {

    private static final long serialVersionUID = 6109454317977451808L;

    /**
     * {@literal @}Nullable
     */
    private GetKnowledgeGraphQuery query;
}
