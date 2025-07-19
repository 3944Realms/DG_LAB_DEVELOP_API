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
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxData;
import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxDataWithAttachment;

import java.io.IOException;

/**
 * The type Power box data wa adapter.
 */
@Deprecated
public class PowerBoxDataWAAdapter extends PowerBoxDataAdapter {
    @Override
    public void write(JsonWriter out, PowerBoxData value) throws IOException {
        out.beginObject();
        PowerBoxDataWithAttachment newValue = (PowerBoxDataWithAttachment) value;
        out.name("type").value(newValue.getType());
        out.name("clientId").value(newValue.getClientId());
        out.name("targetId").value(newValue.getTargetId());
        out.name("message").value(newValue.getMessage());
        out.name("timer_A").value(newValue.getTimerA());
        out.name("timer_B").value(newValue.getTimerB());
        out.endObject();
    }

    @Override
    public PowerBoxData read(JsonReader in) throws IOException {
        String type = "";
        String clientId = "";
        String targetId = "";
        String message = "";
        Integer timer_A = null;
        Integer timer_B = null;

        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "type" -> type = in.nextString();
                case "clientId" -> clientId = in.nextString();
                case "targetId" -> targetId = in.nextString();
                case "message" -> message = in.nextString();
                case "timerA" -> timer_A = in.nextInt();
                case "timerB" -> timer_B = in.nextInt();
            }
        }
        in.endObject();

        if ("POWER_BOX".equals(type)) {
            return new PowerBoxDataWithAttachment(new PowerBoxData(type, clientId, targetId, message), timer_A, timer_B);
        }
        // Handle other types
        throw new JsonParseException("Unknown type: " + type);
    }
}
