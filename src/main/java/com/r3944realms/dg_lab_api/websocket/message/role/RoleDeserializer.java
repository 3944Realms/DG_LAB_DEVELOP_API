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

import com.google.gson.*;
import com.r3944realms.dg_lab_api.websocket.message.role.type.RoleType;

import java.lang.reflect.Type;

/**
 * Role Json反序列化适配器
 */
public class RoleDeserializer implements JsonDeserializer<Role> {

    @Override
    public Role deserialize(JsonElement json, Type typeOFT, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        RoleType type = RoleType.getTypeFromString(jsonObject.get("type").getAsString());
        if (type != null) {
            return switch (type){
                case T_CLIENT -> new WebSocketClientRole(name);
                case T_SERVER -> new WebSocketServerRole(name);
                case APPLICATION -> new WebSocketApplicationRole(name);
                case PLACEHOLDER -> new PlaceholderRole(name);
            };
        }
        return null;
    }
}
