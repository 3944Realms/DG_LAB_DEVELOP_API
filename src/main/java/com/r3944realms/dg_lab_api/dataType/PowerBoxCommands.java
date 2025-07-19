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

package com.r3944realms.dg_lab_api.dataType;

import com.r3944realms.dg_lab_api.websocket.message.data.type.PowerBoxDataType;

/**
 * The type Power box commands.
 */
public final class PowerBoxCommands {
    /**
     * The constant STRENGTH.
     */
    public static final String STRENGTH = "strength";
    /**
     * The constant PULSE.
     */
    public static final String PULSE = "pulse";
    /**
     * The constant CLEAR.
     */
    public static final String CLEAR = "clear";
    /**
     * The constant FEEDBACK.
     */
    public static final String FEEDBACK = "feedback";

    /**
     * Gets command type.
     *
     * @param commandPrefix the command prefix
     * @return the command type
     */
    static String getCommandType(PowerBoxDataType commandPrefix) {
        return switch (commandPrefix) {
            case STRENGTH -> STRENGTH;
            case PULSE -> PULSE;
            case CLEAR -> CLEAR;
            case FEEDBACK -> FEEDBACK;
            default -> PowerBoxMsgType.UNKNOWN;
        };
    }
}
