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

package com.r3944realms.dg_lab_api.message.data;

import java.security.InvalidParameterException;

/**
 * 定义了100ms内部的4端波形数据<br/>
 * <code>frequency</code> 范围在10~240里<br/>
 * <code>strength</code> 范围在0~100里
 *
 * @param f1 第一个25ms的波频
 * @param f2 第二个25ms的波频
 * @param f3 第三个25ms的波频
 * @param f4 第四个25ms的波频
 * @param s1 第一个25ms的强度
 * @param s2 第二个25ms的强度
 * @param s3 第三个25ms的强度
 * @param s4 第四个25ms的强度
 */
public record PulseWave(int f1, int f2, int f3, int f4, int s1, int s2, int s3, int s4) {
    /**
     * Instantiates a new Pulse wave.
     *
     * @param f1 the f 1
     * @param f2 the f 2
     * @param f3 the f 3
     * @param f4 the f 4
     * @param s1 the s 1
     * @param s2 the s 2
     * @param s3 the s 3
     * @param s4 the s 4
     */
    public PulseWave(int f1, int f2, int f3, int f4, int s1, int s2, int s3, int s4) {
        this.f1 = validateFrequency(f1);
        this.f2 = validateFrequency(f2);
        this.f3 = validateFrequency(f3);
        this.f4 = validateFrequency(f4);
        this.s1 = validateStrength(s1);
        this.s2 = validateStrength(s2);
        this.s3 = validateStrength(s3);
        this.s4 = validateStrength(s4);
    }

    /**
     * Instantiates a new Pulse wave.
     *
     * @param frequencies the frequencies
     * @param strengths   the strengths
     */
    PulseWave(int[] frequencies, int[] strengths) {
        this(frequencies[0], frequencies[1], frequencies[2], frequencies[3], strengths[0], strengths[1], strengths[2], strengths[3]);
    }

    /**
     * From default pulse wave.
     *
     * @param f1 the f 1
     * @param f2 the f 2
     * @param f3 the f 3
     * @param f4 the f 4
     * @param s1 the s 1
     * @param s2 the s 2
     * @param s3 the s 3
     * @param s4 the s 4
     * @return the pulse wave
     */
    public static PulseWave fromDefault(int f1, int f2, int f3, int f4, int s1, int s2, int s3, int s4) {
        return new PulseWave(f1, f2, f3, f4, s1, s2, s3, s4);
    }

    /**
     * From arrays pulse wave.
     *
     * @param frequencies the frequencies
     * @param strengths   the strengths
     * @return the pulse wave
     */
    public static PulseWave fromArrays(int[] frequencies, int[] strengths) {
        return new PulseWave(frequencies, strengths);
    }

    /**
     * 根据字符串还原记录
     *
     * @param hex 哈希16进制字符串
     * @return PulseWave pulse wave
     */
    public static PulseWave fromHex(String hex) {
        if (hex == null || !hex.toUpperCase().matches("^([0-9A-F]{2}){8}$")) {
            throw new IllegalArgumentException("Invalid hex string");
        }

        // 解析前8字节为频率（f1-f4）
        int[] frequencies = parseHexChunk(hex.substring(0, 8), "frequency");
        int[] strengths = parseHexChunk(hex.substring(8, 16), "strength");

        return PulseWave.fromArrays(frequencies, strengths);
    }
    @Override
    public String toString() {
        return PulseWaveListGenerator.gson.toJson(this); // Serialize to JSON string
    }

    /**
     * From string pulse wave.
     *
     * @param str the str
     * @return the pulse wave
     */
    public static PulseWave fromString(String str) {
        return PulseWaveListGenerator.gson.fromJson(str, PulseWave.class);
    }

    /**
     * To hex string string.
     *
     * @return the string
     */
    public String toHexString() {
        return String.format("%02X%02X%02X%02X%02X%02X%02X%02X", f1, f2, f3, f4, s1, s2, s3, s4);
    }
    private static int[] parseHexChunk(String hex, String type) {
        int[] values = new int[4];
        for (int i = 0; i < 4; i++) {
            String byteStr = hex.substring(i * 2, (i + 1) * 2);
            int value = Integer.parseInt(byteStr, 16);

            if ("frequency".equals(type)) validateFrequency(value);
            else if ("strength".equals(type)) validateStrength(value);

            values[i] = value;
        }
        return values;
    }
    private static int validateFrequency(int frequency) throws InvalidParameterException{
        if (frequency < 10 || frequency > 240)
            throw new IllegalArgumentException("Frequency must be between 10 and 240");
        return frequency;
    }
    private static int validateStrength(int strength) throws InvalidParameterException{
        if (strength < 0 || strength > 100)
            throw new IllegalArgumentException("Strength must be between 0 and 100");
        return strength;
    }

}
