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

package com.r3944realms.dg_lab_api.message.adapter;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.r3944realms.dg_lab_api.message.data.PulseWave;
import com.r3944realms.dg_lab_api.message.data.PulseWaveList;
import com.r3944realms.dg_lab_api.message.data.PulseWaveListGenerator;

import java.io.IOException;
import java.util.List;

/**
 * The type Pulse wave list adapter.
 */
public class PulseWaveListAdapter extends TypeAdapter<PulseWaveList> {

    @Override
    public void write(JsonWriter out, PulseWaveList value) throws IOException {
        out.beginObject();
        out.name("name").value(value.getName());
        out.name("list");
        writeList(out, value.getList());
        out.endObject();
    }

    @Override
    public PulseWaveList read(JsonReader in) throws IOException {
        PulseWaveList waveList = new PulseWaveList();
        boolean hasName = false;
        boolean hasList = false;
        in.beginObject();
        while (in.hasNext()) {
            String field = in.nextName();
            switch (field) {
                case "name":
                    waveList.setName(in.nextString());
                    hasName = true;
                    break;
                case "list":
                    readList(in, waveList);
                    hasList = true;
                    break;
                default:
                    // 严格模式下可抛出异常
                    // throw new JsonParseException("未知字段: " + field);
                    in.skipValue(); // 宽松模式忽略未知字段
            }

        }
        in.endObject();
        if (!hasName) {
            throw new JsonParseException("Missing required field: name");
        }
        if (!hasList) {
            throw new JsonParseException("Missing required field: list");
        }
        return waveList;
    }
    private void writeList(JsonWriter out, List<PulseWave> list) throws IOException {
        out.beginArray();
        for (PulseWave wave : list) {
            PulseWaveListGenerator.gson.toJson(wave, PulseWave.class, out);
        }
        out.endArray();
    }

    private void readList(JsonReader in, PulseWaveList waveList) throws IOException {
        in.beginArray();
        while (in.hasNext()) {
            if (in.peek() == JsonToken.STRING) {
                // Parse string to PulseWave (adjust based on toHexString() format)
                waveList.add(parseWaveFromString(in.nextString()));
            } else {
                // Existing logic for object parsing
                waveList.add(PulseWaveListGenerator.gson.getAdapter(PulseWave.class).read(in));
            }
        }
        in.endArray();
    }
    private PulseWave parseWaveFromString(String waveString) {
        // Implement parsing logic based on PulseWave's toHexString() format
        // Example: If toHexString() returns JSON, use:
        return PulseWave.fromString(waveString);
    }
}
