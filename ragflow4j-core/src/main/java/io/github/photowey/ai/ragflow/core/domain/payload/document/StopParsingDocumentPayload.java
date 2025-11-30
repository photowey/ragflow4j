package io.github.photowey.ai.ragflow.core.domain.payload.document;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.photowey.ai.ragflow.core.domain.payload.AbstractPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Stop parsing documents.
 *
 * @author photowey
 * @version 1.0.0
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#stop-parsing-documents">Stop parsing documents</a>
 * @since 2025/11/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StopParsingDocumentPayload extends AbstractPayload {

    private static final long serialVersionUID = -8928398832119323659L;

    @NotEmpty(message = "Document IDs must not be empty")
    @JsonProperty("document_ids")
    private List<String> documentIds;
}
