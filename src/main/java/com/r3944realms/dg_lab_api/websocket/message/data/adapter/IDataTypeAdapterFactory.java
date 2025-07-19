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

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.r3944realms.dg_lab_api.websocket.message.data.IData;
import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxData;
import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxDataWithAttachment;
import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxDataWithSingleAttachment;

import java.io.IOException;

/**
 * The type Data type adapter factory.
 */
@SuppressWarnings("deprecation")
public class IDataTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        if(!IData.class.isAssignableFrom(typeToken.getRawType())) {
            return null;
        }
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
        final TypeAdapter<PowerBoxData> powerBoxDataAdapter = gson.getDelegateAdapter(this, TypeToken.get(PowerBoxData.class));
        final TypeAdapter<PowerBoxDataWithAttachment> powerBoxDataWithAttachmentAdapter = gson.getDelegateAdapter(this, TypeToken.get(PowerBoxDataWithAttachment.class));
        final TypeAdapter<PowerBoxDataWithSingleAttachment> powerBoxDataWithSingleAttachmentTypeAdapter = gson.getDelegateAdapter(this, TypeToken.get(PowerBoxDataWithSingleAttachment.class));
        return (TypeAdapter<T>) new TypeAdapter<IData>() {
            @Override
            public void write(JsonWriter jsonWriter, IData iData) throws IOException {
                JsonElement jsonElement = switch (iData.getClass().getSimpleName()) {
                    case "PowerBoxDataWithAttachment" ->
                            powerBoxDataWithAttachmentAdapter.toJsonTree((PowerBoxDataWithAttachment) iData);
                    case "PowerBoxDataWithSingleAttachment" ->
                            powerBoxDataWithSingleAttachmentTypeAdapter.toJsonTree((PowerBoxDataWithSingleAttachment) iData);
                    case "PowerBoxData" -> powerBoxDataAdapter.toJsonTree((PowerBoxData) iData);
                    case "null" ->
                            throw new NullPointerException("IDataTypeAdapterFactory#create(Gson gson, TypeToken iData): null");
                    default -> throw new JsonSyntaxException("Unsupported data type: " + iData.getClass().getName());
                };
                elementAdapter.write(jsonWriter, jsonElement);
            }

            @Override
            public IData read(JsonReader jsonReader) throws IOException {
                JsonElement element = elementAdapter.read(jsonReader);
                JsonObject jsonObject = element.getAsJsonObject();

                JsonElement type = jsonObject.get("type");

                String Eigenvalues_A = type.getAsString();
                switch (Eigenvalues_A) {
                    case "heartbeat", "error", "msg", "break", "bind" -> {
                        return powerBoxDataAdapter.fromJsonTree(element);
                    }
                    case "clientMsg" -> {
                        return powerBoxDataWithSingleAttachmentTypeAdapter.fromJsonTree(element);
                    }
                    default -> throw new JsonParseException("Unknown type");

                }
            }
        };

    }
}
