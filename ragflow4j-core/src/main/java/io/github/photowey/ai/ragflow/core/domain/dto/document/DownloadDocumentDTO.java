package io.github.photowey.ai.ragflow.core.domain.dto.document;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Download document.
 *
 * @author photowey
 * @version 1.0.0
 * @see <a href="https://ragflow.io/docs/v0.22.1/http_api_reference#download-document">Download document</a>
 * @since 2025/11/30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DownloadDocumentDTO implements Serializable {

    private static final long serialVersionUID = 1827453946696218990L;

    private Integer code;
    private String message;

    private String filename;
    private byte[] data;

    // ----------------------------------------------------------------

    public boolean determineIsOk() {
        if (Objects.isNull(this.code)) {
            return false;
        }

        return RAGFlowDictionary.ErrorCode.determineIsOk(this.code);
    }

}
