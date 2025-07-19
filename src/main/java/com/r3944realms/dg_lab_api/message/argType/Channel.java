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

package com.r3944realms.dg_lab_api.message.argType;

/**
 * 通道
 */
public enum Channel {
    /**
     * A频道
     */
    A(1, 'A'),
    /**
     * B频道
     */
    B(2, 'B');
    /**
     * 通道int值
     */
    public final int index_int;
    /**
     * 通道char值
     */
    public final char index_char;

    Channel(int index_int, char index_char) {
        this.index_int = index_int;
        this.index_char = index_char;
    }

    /**
     * Gets channel.
     *
     * @param index_int the index int
     * @return the channel
     */
    public static Channel getChannel(int index_int) {
        return index_int == 1 ? A : B;
    }

    /**
     * Gets channel.
     *
     * @param index_char the index char
     * @return the channel
     */
    public static Channel getChannel(char index_char) {
        return index_char == 'A' ? A : B;
    }
}
