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

package com.r3944realms.dg_lab_api.websocket.message.role;

import com.r3944realms.dg_lab_api.websocket.message.role.type.RoleType;

import java.io.Serializable;

/**
 * 角色，是{@link com.r3944realms.dg_lab.websocket.message.MessageDirection messageDirection}组成部分
 */
public sealed abstract class Role implements Serializable
        permits PlaceholderRole, WebSocketApplicationRole, WebSocketClientRole, WebSocketServerRole {
    /**
     * The Name.
     */
    public String name;
    /**
     * The Type.
     */
    public final RoleType type;

    /**
     * Instantiates a new Role.
     *
     * @param name the name
     * @param type the type
     */
    Role(String name, final RoleType type) {
        this.name = name;
        this.type = type;
    }

    /**
     * 更新角色名字，主要在每次心跳时更新
     *
     * @param name 角色
     */
    public void UpdateName(String name) {
        this.name = name;
    }
}

