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

package com.r3944realms.dg_lab_api.websocket.message;

import com.google.gson.*;
import com.r3944realms.dg_lab_api.websocket.message.role.Role;

import java.lang.reflect.Type;

/**
 * The type Message direction deserializer.
 */
public class MessageDirectionDeserializer implements JsonDeserializer<MessageDirection<?, ?>> {
    @Override
    public MessageDirection<?, ?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        JsonElement senderJson = obj.get("sender");
        JsonElement receiverJson = obj.get("receiver");

        Role sender = jsonDeserializationContext.deserialize(senderJson, Role.class);
        Role receiver = jsonDeserializationContext.deserialize(receiverJson, Role.class);
        return new MessageDirection<>(sender, receiver);
    }
}
