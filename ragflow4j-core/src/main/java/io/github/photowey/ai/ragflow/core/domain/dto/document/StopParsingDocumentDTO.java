package io.github.photowey.ai.ragflow.core.domain.dto.document;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.github.photowey.ai.ragflow.core.domain.dto.EmptyDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

// @formatter:off

/**
 * Stop parsing documents.
 *
 * @author photowey
 * @version 1.0.0
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#stop-parsing-documents">Stop parsing documents</a>
 * @since 2025/11/30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class StopParsingDocumentDTO extends EmptyDTO { }

// @formatter:on
