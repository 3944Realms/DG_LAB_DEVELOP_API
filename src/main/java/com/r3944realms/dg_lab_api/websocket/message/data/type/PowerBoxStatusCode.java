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
 * 状态码
 */
public enum PowerBoxStatusCode {
    /**
     * 200 - 成功
     */
    SUCCESSFUL("200"),
    /**
     * 209 - 对方客户端已断开
     */
    OPPOSITE_CLIENT_DISCONNECTED("209"),
    /**
     * 210 - 二维码中没有有效的clientID
     */
    QR_CODE_HAS_A_INVALID_CLIENT_ID("210"),
    /**
     * 211 - socket连接上了，但服务器迟迟不下发app端的id来绑定
     */
    WAITING_FOR_SERVER_BINDING_MESSAGE_TOO_LONG("211"),
    /**
     * 400 - 此id已被其他客户端绑定关系
     */
    TRYING_BINDING_ALREADY_BOUND_ID("400"),
    /**
     * 401 - 要绑定的目标客户端不存在
     */
    TARGET_CLIENT_NOT_EXIST("401"),
    /**
     * 402 - 收信方和寄信方不是绑定关系
     */
    NOT_BINDING_RELATIONSHIP("402"),
    /**
     * 403 - 发送的内容不是标准json对象
     */
    NOT_STANDARD_JSON("403"),
    /**
     * 404 - 未找到收信人（离线）
     */
    NOT_FOUND_BECAUSE_OF_OFFLINE("404"),
    /**
     * 405 - 下发的message长度大于1950
     */
    MESSAGE_TOO_LONG("405"),
    /**
     * 406 - 未指定通道
     */
    NO_CHOOSE_CHANNEL("406"),
    /**
     * 500 - 服务器内部异常
     */
    INTERNAL_ERROR("500"),
    /**
     * 501 - 客户端发送了无效信息（Message为无效内容）给服务器
     */
    INVALID_REQUEST("501"),
    /**
     * 502 - 不支持的操作
     */
    UNSUPPORTED_OPERATION("502"),
    /**
     * -1  -  无效状态码
     */
    INVALID_STATUS_CODE("-1");
    /**
     * The Code.
     */
    final String code;
    PowerBoxStatusCode(String code) {
        this.code = code;
    }

    /**
     * Gets status code.
     *
     * @param code the code
     * @return the status code
     */
    public static PowerBoxStatusCode getStatusCode(String code) {
        return switch(code) {
            case "200" -> PowerBoxStatusCode.SUCCESSFUL;
            case "209" -> PowerBoxStatusCode.OPPOSITE_CLIENT_DISCONNECTED;
            case "210" -> PowerBoxStatusCode.QR_CODE_HAS_A_INVALID_CLIENT_ID;
            case "211" -> PowerBoxStatusCode.WAITING_FOR_SERVER_BINDING_MESSAGE_TOO_LONG;
            case "400" -> PowerBoxStatusCode.TRYING_BINDING_ALREADY_BOUND_ID;
            case "401" -> PowerBoxStatusCode.TARGET_CLIENT_NOT_EXIST;
            case "402" -> PowerBoxStatusCode.NOT_BINDING_RELATIONSHIP;
            case "403" -> PowerBoxStatusCode.NOT_STANDARD_JSON;
            case "404" -> PowerBoxStatusCode.NOT_FOUND_BECAUSE_OF_OFFLINE;
            case "405" -> PowerBoxStatusCode.MESSAGE_TOO_LONG;
            case "406" -> PowerBoxStatusCode.NO_CHOOSE_CHANNEL;
            case "500" -> PowerBoxStatusCode.INTERNAL_ERROR;
            case "501" -> PowerBoxStatusCode.INVALID_REQUEST;
            case "502" -> PowerBoxStatusCode.UNSUPPORTED_OPERATION;
            default -> PowerBoxStatusCode.INVALID_STATUS_CODE;
        };
    }

    /**
     * Is valid status code boolean.
     *
     * @param code the code
     * @return the boolean
     */
    public static boolean isValidStatusCode(String code) {
        return getStatusCode(code) != INVALID_STATUS_CODE;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }
}
