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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.r3944realms.dg_lab_api.message.adapter.PulseWaveAdapter;
import com.r3944realms.dg_lab_api.message.adapter.PulseWaveListAdapter;

import java.util.stream.IntStream;

/**
 * The type Pulse wave list generator.
 */
public class PulseWaveListGenerator {
    /**
     * The constant gson.
     */
    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(PulseWaveList.class, new PulseWaveListAdapter())
            .registerTypeAdapter(PulseWave.class, new PulseWaveAdapter())
            .setPrettyPrinting()
            .create();

    private PulseWaveListGenerator() {}

    /**
     * To pulse wave list from string array pulse wave list.
     *
     * @param waveStringList the wave string list
     * @return the pulse wave list
     */
    public static PulseWaveList toPulseWaveListFromStringArray(String[] waveStringList) {
        PulseWaveList ret = new PulseWaveList();
        for (String s : waveStringList) {
            int v1, v2, v3, v4, v5, v6, v7, v8;
            int i = 0;
            v1 = Integer.parseInt(s.substring(i, 2 + i++), 16);
            v2 = Integer.parseInt(s.substring(++i, 2 + i++), 16);
            v3 = Integer.parseInt(s.substring(++i, 2 + i++), 16);
            v4 = Integer.parseInt(s.substring(++i, 2 + i++), 16);
            v5 = Integer.parseInt(s.substring(++i, 2 + i++), 16);
            v6 = Integer.parseInt(s.substring(++i, 2 + i++), 16);
            v7 = Integer.parseInt(s.substring(++i, 2 + i++), 16);
            v8 = Integer.parseInt(s.substring(++i, 2 + i), 16);
            ret.add(new PulseWave(v1, v2, v3, v4, v5, v6, v7, v8));
        }
        return ret;
    }

    /**
     * 输入频率和强度数组，输出波形列表 <br/>
     * 数组长度必须一致，且为4的倍数。
     *
     * @param frequencies 频率数组
     * @param strengths   强度数组
     * @return 波形列表 pulse wave list
     * @throws IllegalArgumentException 不符合条件的输入
     */
    public static PulseWaveList pulseWave(int[] frequencies, int[] strengths) throws IllegalArgumentException {
        if (frequencies.length != strengths.length)
            throw new IllegalArgumentException("frequencies and strengths must be the same length");
        if (frequencies.length % 4 != 0)
            throw new IllegalArgumentException("frequencies must be a multiple of 4");
        PulseWaveList ret = new PulseWaveList();
        for (int i = 0; i < frequencies.length; i += 4) {
            ret.add(
                    new PulseWave(
                        frequencies[i], frequencies[i + 1], frequencies[i + 2], frequencies[i + 3],
                        strengths[i], strengths[i + 1], strengths[i + 2], strengths[i + 3]
                    )
            );
        }
        return ret;
    }

    /**
     * 输入频率和强度混合数组，输出波形列表 <br/>
     *
     * @param args 频率和强度混合数组
     * @return 波形列表 pulse wave list
     * @throws IllegalArgumentException 不符合条件的输入
     */
    public static PulseWaveList pulseWave(int[] args) throws IllegalArgumentException {
        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("the number of arguments must be a multiple of 2");
        }
        int[] frequencies = new int[args.length / 2];
        int[] strengths = new int[args.length / 2];
        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0)
                frequencies[i] = args[i];
            else
                strengths[i] = args[i];
        }
        return pulseWave(frequencies, strengths);
    }

    /**
     * 生成正弦波
     *
     * @param frequency   波的频率
     * @param minStrength 波的最小强度
     * @param maxStrength 波的最大强度
     * @param duration    波的时长d (d 是8的倍数，实际时长 = d * 25ms)
     * @return 生成的正弦的波 pulse wave list
     * @throws IllegalArgumentException 不符合条件的输入
     */
    public static PulseWaveList sinPulse(
            int frequency, int minStrength, int maxStrength, int duration) throws IllegalArgumentException {
        if (duration <= 0)
            throw new IllegalArgumentException("duration must be greater than 0");
        int validDataNumber = duration - duration % 4;
        int[] strengths = new int[validDataNumber];
        // 振幅
        double amplitude = maxStrength - minStrength;
        // 角度增量, 只需要 0 - π 的范围
        double angleStep = Math.PI / (duration - 1);
        for (int i = 0; i < validDataNumber; i++) {
            // 当前角度
            double angle = i * angleStep;
            // 计算正弦值，并且平移到给定的最大最小值
            double sinValue = Math.sin(angle) * amplitude + minStrength;
            strengths[i] = ((int) Math.round(sinValue));
        }
        return pulseWave(IntStream.generate(() -> frequency).limit(validDataNumber).toArray(), strengths);
    }

    /**
     * 生成梯度的波
     *
     * @param frequency     波的频率
     * @param startStrength 起始强度
     * @param endStrength   最终强度
     * @param duration      波的时长d (d 是8的倍数，实际时长 = d * 25ms)
     * @return 生成的梯度的波 pulse wave list
     * @throws IllegalArgumentException 不符合条件的输入
     */
    public static PulseWaveList gradientPulse(
            int frequency, int startStrength, int endStrength, int duration) throws IllegalArgumentException {
        if (duration <= 0)
            throw new IllegalArgumentException("duration must be greater than 0");
        duration = duration - duration % 4;
        int[] strengths = new int[duration];
        double step = (endStrength - startStrength) / (duration - 1.0);
        for (int i = 0; i < duration; i++) {
            strengths[i] = ((int) Math.round(startStrength + step * i));
        }
        return pulseWave(IntStream.generate(() -> frequency).limit(duration).toArray(), strengths);
    }

    /**
     * 生成平滑的波
     *
     * @param frequency 波的频率
     * @param strength  波的强度
     * @param duration  波的时长d (d 是8的倍数，实际时长 = d * 25ms)
     * @return 生成的平滑的波 pulse wave list
     * @throws IllegalArgumentException the illegal argument exception
     */
    public static PulseWaveList smoothPulse(int frequency, int strength, int duration) throws IllegalArgumentException {
        if (duration <= 0) {
            throw new IllegalArgumentException("duration must be greater than 0");
        }
        duration = duration - duration % 4;
        return pulseWave(
                IntStream.generate(() -> frequency).limit(duration).toArray(),
                IntStream.generate(() -> strength).limit(duration).toArray()
        );
    }
}
