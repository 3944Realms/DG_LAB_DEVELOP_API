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

package com.r3944realms.dg_lab_api.websocket.message.data.adapter;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxData;

import java.io.IOException;

/**
 * The type Power box data adapter.
 */
public class PowerBoxDataAdapter extends TypeAdapter<PowerBoxData> {
        @Override
        public void write(JsonWriter out, PowerBoxData value) throws IOException {
            out.beginObject();
            out.name("type").value(value.getType());
            out.name("clientId").value(value.getClientId());
            out.name("targetId").value(value.getTargetId());
            out.name("message").value(value.getMessage());
            out.endObject();
        }

        @Override
        public PowerBoxData read(JsonReader in) throws IOException {
            String type = "";
            String clientId = "";
            String targetId = "";
            String message = "";

            in.beginObject();
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "type":
                        type = in.nextString();
                        break;
                    case "clientId":
                        clientId = in.nextString();
                        break;
                    case "targetId":
                        targetId = in.nextString();
                        break;
                    case "message":
                        message = in.nextString();
                        break;
                }
            }
            in.endObject();

            if ("POWER_BOX".equals(type)) {
                return new PowerBoxData(type, clientId, targetId, message);
            }
            // Handle other types
            throw new JsonParseException("Unknown type: " + type);
        }
}


