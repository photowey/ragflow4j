package io.github.photowey.ai.ragflow.core.domain.context.document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import io.github.photowey.ai.ragflow.core.domain.context.AbstractContext;
import io.github.photowey.ai.ragflow.core.domain.payload.document.StopParsingDocumentPayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Stop parsing documents.
 *
 * @author photowey
 * @version 1.0.0
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#stop-parsing-documents">Stop parsing documents</a>
 * @since 2025/11/30
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class StopParsingDocumentContext extends AbstractContext {

    private static final long serialVersionUID = -6560603205373897086L;

    @Valid
    @NotNull(message = "Payload must not be null")
    private StopParsingDocumentPayload payload;
}
