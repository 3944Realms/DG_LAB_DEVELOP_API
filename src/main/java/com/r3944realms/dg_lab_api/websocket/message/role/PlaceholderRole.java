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

/**
 * WS占位角色
 */
public final class PlaceholderRole extends Role {
    /**
     * Instantiates a new Placeholder role.
     *
     * @param name the name
     */
    public PlaceholderRole(String name) {
        super(name, RoleType.PLACEHOLDER);
    }

    /**
     * Of placeholder role.
     *
     * @param name the name
     * @return the placeholder role
     */
    public static PlaceholderRole of(String name) {
        return new PlaceholderRole("Pl" + name);
    }
}
