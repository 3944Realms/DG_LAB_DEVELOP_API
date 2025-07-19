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

package com.r3944realms.dg_lab_api.websocket.message.data.type;

/**
 * PowerBox数据类型
 */
public enum PowerBoxDataType {
    /**
     * 心跳类型(无参数)
     */
    _NC_HEARTBEAT_,
    /**
     * 绑定类型(无参数)
     */
    _NC_BIND_,
    /**
     * 断开类型(无参数)
     */
    _NC_BREAK_,
    /**
     * 错误类型(无参数)
     */
    _NC_ERROR_,
    /**
     * 强度类型 (参数数量：3~4)
     */
    STRENGTH(3, 4),
    /**
     * 波形类型 (参数数量：1~101)
     */
    PULSE(1, 101),
    /**
     * 清空类型 (参数数量：1)
     */
    CLEAR(1),
    /**
     * 反馈类型 (参数数量：1)
     */
    FEEDBACK(1),
    /**
     * 客户端消息类型 (无参数)
     */
    CLIENT_MESSAGE,
    /**
     * 未知类型 (无参数)
     */
    UNKNOWN;
    /**
     * The Nop.
     */
    public final int NOP;
    /**
     * The Max nop.
     */
    public final int MaxNOP;
    PowerBoxDataType() {
        this(0, 0);
    }
    PowerBoxDataType(int NumberOfParameters) {
        this(NumberOfParameters,-1);
    }
    PowerBoxDataType(int minNumberOfParameters, int maxNumberOfParameters) {
        this.NOP = minNumberOfParameters;
        this.MaxNOP = maxNumberOfParameters;
    }
    private static PowerBoxDataType getCommandType(String commandPrefix) {
        return switch (commandPrefix) {
            case "strength" -> STRENGTH;
            case "pulse" -> PULSE;
            case "clear" -> CLEAR;
            case "feedback" -> FEEDBACK;
            default -> UNKNOWN;
        };
    }

    /**
     * Gets type.
     *
     * @param type the type
     * @param msg  the msg
     * @return the type
     */
    public static PowerBoxDataType getType(String type, String msg) {
        return getType(type, msg, false);
    }

    /**
     * Gets type.
     *
     * @param type     the type
     * @param msg      the msg
     * @param mayPulse the may pulse
     * @return the type
     */
    public static PowerBoxDataType getType(String type, String msg, boolean mayPulse) {
        return switch (type) {
            case "heartbeat" -> _NC_HEARTBEAT_;
            case "bind" -> _NC_BIND_;
            case "msg" -> getCommandType(msg.split("-")[0]);
            case "break" -> _NC_BREAK_;
            case "error" -> _NC_ERROR_;
            case "clientMsg" -> {
                PowerBoxDataType commandType = getCommandType(msg.split("-")[0]);
                yield mayPulse && commandType == PULSE ? PULSE : CLIENT_MESSAGE;
            }
            default -> UNKNOWN;
        };
    }
}
