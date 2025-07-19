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
 * The type Power box msg type.
 */
public final class PowerBoxMsgType {
    /**
     * The constant HEARTBEAT.
     */
    public static final String HEARTBEAT = "heartbeat";
    /**
     * The constant BIND.
     */
    public static final String BIND = "bind";
    /**
     * The constant BREAK.
     */
    public static final String BREAK = "break";
    /**
     * The constant MSG_COMMAND.
     */
    public static final String MSG_COMMAND = "msg";
    /**
     * The constant ERROR.
     */
    public static final String ERROR = "error";
    /**
     * The constant CLIENT_MSG.
     */
    public static final String CLIENT_MSG = "clientMsg";
    /**
     * The constant UNKNOWN.
     */
    public static final String UNKNOWN = "unknown";

    /**
     * Gets msg type.
     *
     * @param type the type
     * @return the msg type
     */
    public static String getMsgType(PowerBoxDataType type) {
        return getMsgType(type, false);
    }

    /**
     * Gets msg type.
     *
     * @param type           the type
     * @param showMsgCommand the show msg command
     * @return the msg type
     */
    public static String getMsgType(PowerBoxDataType type, boolean showMsgCommand) {
        return switch (type) {
            case _NC_HEARTBEAT_ -> HEARTBEAT;
            case _NC_BIND_ -> BIND;
            case _NC_BREAK_ -> BREAK;
            case _NC_ERROR_ -> ERROR;
            case CLIENT_MESSAGE -> CLIENT_MSG;
            case STRENGTH, CLEAR, PULSE, FEEDBACK -> showMsgCommand ? PowerBoxCommands.getCommandType(type) : MSG_COMMAND;
            default -> UNKNOWN;
        };
    }
}
