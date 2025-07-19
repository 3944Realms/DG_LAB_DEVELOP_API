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

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * 波形列表
 */
public class PulseWaveList {
    private String name;
    private final List<PulseWave> list;

    /**
     * Instantiates a new Pulse wave list.
     */
    public PulseWaveList() {
        this.name = "";
        list = new ArrayList<>();
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Add.
     *
     * @param wave the wave
     */
    public void add(PulseWave wave) {
        list.add(wave);
    }

    /**
     * Clear.
     */
    public void clear() {
        list.clear();
    }

    /**
     * Gets list.
     *
     * @return the list
     */
    public List<PulseWave> getList() {
        return ImmutableList.copyOf(list);
    }

    /**
     * To list string.
     *
     * @return the string
     */
    public String toListString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i <= list.size() -1; i++) {
            sb.append("\"").append(list.get(i).toHexString()).append("\"");
            if(i != list.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
    @Override
    public String toString() {
        return "PulseWaveList{name='" + name + "', list=" + list + "}";
    }

}
