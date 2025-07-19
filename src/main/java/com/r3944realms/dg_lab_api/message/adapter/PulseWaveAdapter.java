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

import com.google.gson.*;
import com.r3944realms.dg_lab_api.message.data.PulseWave;

import java.lang.reflect.Type;

/**
 * 处理 PulseWave 的 JSON 格式：
 * - 格式1: "A1B2C3D4E5F6G7H8" (16进制字符串)
 * - 格式2: {"frequencies":[20,30,40,50], "strengths":[80,90,70,60]}
 */
public class PulseWaveAdapter implements JsonSerializer<PulseWave>, JsonDeserializer<PulseWave> {
    @Override
    public PulseWave deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if (json.isJsonPrimitive()) {
                String hex = json.getAsString();
                return parseHexString(hex);
            } else if (json.isJsonObject()) {
                 return parseObject(json.getAsJsonObject());
            } else
                throw new JsonParseException("Invalid PulseWave format: expected String or Object");
        } catch (Exception e) {
            throw new JsonParseException("PulseWave validation failed: " + e.getMessage(), e);
        }
    }

    @Override
    public JsonElement serialize(PulseWave src, Type typeOfSrc, JsonSerializationContext context) {
        // 默认序列化为16进制字符串（如 "0A1E1422805A4632"）
        return new JsonPrimitive(src.toHexString());
    }
    /**
     * 解析16进制字符串（格式：16字符，如 "0A1E1422805A4632"）
     */
    private PulseWave parseHexString(String hex) {
        hex = hex.toUpperCase().trim();
        if (!hex.matches("^[0-9A-F]{16}$")) {
            throw new JsonParseException("Invalid hex format: must be 16 uppercase hex characters");
        }
        return PulseWave.fromHex(hex);
    }
    /**
     * 解析对象格式（{"frequencies":[...], "strengths":[...]}）
     */
    private PulseWave parseObject(JsonObject obj) {
        // 解析频率数组
        int[] frequencies = parseJsonArray(obj.get("frequencies"), "frequencies");
        // 解析强度数组
        int[] strengths = parseJsonArray(obj.get("strengths"), "strengths");
        return PulseWave.fromArrays(frequencies, strengths);
    }
    /**
     * 解析JSON数组并校验长度和类型
     */
    private int[] parseJsonArray(JsonElement element, String fieldName) {
        if (element == null || !element.isJsonArray()) {
            throw new JsonParseException("Missing required field: " + fieldName);
        }

        JsonArray array = element.getAsJsonArray();
        if (array.size() != 4) {
            throw new JsonParseException(fieldName + " must have exactly 4 elements");
        }

        int[] values = new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                values[i] = array.get(i).getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonParseException("Invalid number in " + fieldName + " at index " + i, e);
            }
        }
        return values;
    }

}
