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

package com.r3944realms.dg_lab_api.websocket.message.role.type;

/**
 * 角色枚举类型
 */
public enum RoleType {
    /**
     * Tradition Server 传统意义上的服务器
     */
    T_SERVER,
    /**
     * Tradition Client 传统意义上的客户端
     */
    T_CLIENT,
    /**
     * App 应用
     */
    APPLICATION,
    /**
     * 占位符 即任意端
     */
    PLACEHOLDER;

    /**
     * Gets type from string.
     *
     * @param string the string
     * @return the type from string
     */
    public static RoleType getTypeFromString(String string) {
        return switch (string) {
            case "T_SERVER" -> T_SERVER;
            case "T_CLIENT" -> T_CLIENT;
            case "APPLICATION" -> APPLICATION;
            case "PLACEHOLDER" -> PLACEHOLDER;
            default -> null;
        };
    }
}
