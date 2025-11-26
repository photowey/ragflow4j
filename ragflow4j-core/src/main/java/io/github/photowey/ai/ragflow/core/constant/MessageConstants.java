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
package io.github.photowey.ai.ragflow.core.constant;

/**
 * {@code MessageConstants}.
 *
 * @author photowey
 * @version 2025.0.22.0.1
 * @since 2025/11/23
 */
public interface MessageConstants {

    String UNKNOWN_ERROR = "Unknown Error.";
    String RAGFLOW_DEPLOYMENT_KEY_INVALID = "The deploy key(%s) is invalid.";
    String CREATE_DATASET_FAILED = "Failed to create dataset(%s): ";
    String DELETE_DATASET_FAILED = "Failed to delete datasets(%s): ";
    String UPDATE_DATASET_FAILED = "Failed to update dataset(%s): ";
    String LIST_DATASET_FAILED = "Failed to list dataset: ";
}
