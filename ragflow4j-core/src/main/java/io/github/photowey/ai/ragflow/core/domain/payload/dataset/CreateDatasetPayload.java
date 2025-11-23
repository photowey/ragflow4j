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
package io.github.photowey.ai.ragflow.core.domain.payload.dataset;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.photowey.ai.ragflow.core.domain.model.ParserConfig;
import io.github.photowey.ai.ragflow.core.domain.payload.AbstractPayload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * {@code CreateDatasetPayload}.
 *
 * @author weichangjun
 * @version 2025.0.22.0.1
 * @since 2025/11/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateDatasetPayload extends AbstractPayload {

    private static final long serialVersionUID = 872003217692394727L;

    /**
     * The unique name of the dataset to create. It must adhere to the following requirements:
     *
     * <p>
     * Basic Multilingual Plane (BMP) only
     *
     * <p>
     * Maximum 128 characters
     * Case-insensitive
     */
    @NotBlank(message = "name must not be blank")
    @Size(max = 128)
    @JsonProperty("name")
    private String name;

    /**
     * Base64 encoding of the avatar.
     *
     * <p>
     * Maximum 65535 characters
     */
    @Max(65535)
    @JsonProperty("avatar")
    private String avatar;

    /**
     * A brief description of the dataset to create.
     *
     * <p>
     * Maximum 65535 characters
     */
    @Max(65535)
    @JsonProperty("description")
    private String description;

    /**
     * The name of the embedding model to use. For example: "BAAI/bge-large-zh-v1.5@BAAI"
     *
     * <p>
     * Maximum 255 characters
     * Must follow model_name@model_factory format
     */
    @Max(255)
    @JsonProperty("embedding_model")
    private String embeddingModel;

    /**
     * Specifies who can access the dataset to create. Available options:
     *
     * <p>
     * - `"me"`: (Default) Only you can manage the dataset.
     * - `"team"`: All team members can manage the dataset.
     */
    @NotBlank(message = "permission must not be blank")
    @JsonProperty("permission")
    @Pattern(regexp = "^(me|team)$", message = "Permission value not allowed: {0}. Must be 'me' or 'team'.")
    private String permission;

    /**
     * The chunking method of the dataset to create. Available options:
     *
     * <p>
     * - `"naive"`: General (default)
     * - `"book"`: Book
     * - `"email"`: Email
     * - `"laws"`: Laws
     * - `"manual"`: Manual
     * - `"one"`: One
     * - `"paper"`: Paper
     * - `"picture"`: Picture
     * - `"presentation"`: Presentation
     * - `"qa"`: Q&A
     * - `"table"`: Table
     * - `"tag"`: Tag
     *
     * @see io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary.ChunkMethod
     */
    @JsonProperty("chunk_method")
    @NotBlank(message = "chunkMethod must not be blank")
    @Pattern(
        regexp = "^(naive|book|email|laws|manual|one|paper|picture|presentation|qa|table|tag)$",
        message = "Invalid chunk method. Must be one of: naive, book, email, laws, manual, one, "
            + "paper, picture, presentation, qa, table, or tag."
    )
    private String chunkMethod;

    /**
     * The configuration settings for the dataset parser.
     * The attributes in this JSON object vary with the selected "chunk_method"
     *
     * @see ParserConfig
     * @see io.github.photowey.ai.ragflow.core.enums.RAGFlowDictionary.ChunkMethod
     */
    @JsonProperty("parser_config")
    private ParserConfig parserConfig;

    // ----------------------------------------------------------------

    public String name() {
        return name;
    }

    public String avatar() {
        return avatar;
    }

    public String description() {
        return description;
    }

    public String embeddingModel() {
        return embeddingModel;
    }

    public String permission() {
        return permission;
    }

    public String chunkMethod() {
        return chunkMethod;
    }

    public ParserConfig parserConfig() {
        return parserConfig;
    }
}
