/*******************************************************************************
 * Copyright (c) 2024-2025 R3944Realms. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.r3944realms.dg_lab_api.websocket.message.data;

public enum DataType {
    /**
     * Power box att data type.
     */
    POWER_BOX_ATT,
    /**
     * Default data type.
     */
    DEFAULT,
    /**
     * Power box data type.
     */
    POWER_BOX;
    /**
     * Gets type from string.
     *
     * @param type the type
     * @return the type from string
     */
    public static DataType getTypeFromString(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DEFAULT;
        }
    }


}
