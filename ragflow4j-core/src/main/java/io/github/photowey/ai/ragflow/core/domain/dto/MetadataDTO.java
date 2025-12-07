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
package io.github.photowey.ai.ragflow.core.domain.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import lombok.Data;

/**
 * {@code MetadataDTO}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/26
 */
@Data
public class MetadataDTO implements Serializable {

    private static final long serialVersionUID = 1164722216934402435L;

    /**
     * Additional metadata.
     */
    protected Map<String, Object> metadata;

    // ----------------------------------------------------------------

    public Map<String, Object> metadata() {
        return metadata;
    }

    public Optional<Map<String, Object>> tryMetadata() {
        return Optional.ofNullable(metadata);
    }

    public Optional<Object> tryMetadata(String key) {
        return tryMetadata(key, Function.identity());
    }

    public <T> Optional<T> tryMetadata(String key, Function<Object, T> fx) {
        if (Objects.isNull(key) || key.trim().isEmpty()) {
            return Optional.empty();
        }

        Optional<Map<String, Object>> metadataOpt = this.tryMetadata();
        if (metadataOpt.isPresent()) {
            Map<String, Object> meta = metadataOpt.get();
            Optional<Object> objectOpt = Optional.ofNullable(meta.get(key));

            if (objectOpt.isPresent()) {
                return Optional.ofNullable(fx.apply(objectOpt.get()));
            }
        }

        return Optional.empty();
    }

}
