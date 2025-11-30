package io.github.photowey.ai.ragflow.core.domain.context.document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import io.github.photowey.ai.ragflow.core.domain.context.AbstractContext;
import io.github.photowey.ai.ragflow.core.domain.payload.document.DownloadDocumentPayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Download document.
 *
 * @author photowey
 * @version 1.0.0
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#download-document">Download document</a>
 * @since 2025/11/30
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class DownloadDocumentContext extends AbstractContext {

    private static final long serialVersionUID = 6963288387325604082L;

    @Valid
    @NotNull(message = "Payload must not be null")
    private DownloadDocumentPayload payload;
}
