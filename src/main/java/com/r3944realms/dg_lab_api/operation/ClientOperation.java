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

package com.r3944realms.dg_lab_api.operation;

import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxData;

/**
 * The interface Client operation.
 */
public interface ClientOperation  extends IOperation {
    /**
     * 客户端线程开启中处理
     */
    void ClientStartingHandler();

    /**
     * 客户端线程完全开启后处理
     */
    void ClientStartedHandler();

    /**
     * 客户端启动遇到错误后处理
     */
    void ClientStartingErrorHandler();

    /**
     * 客户端线程关闭中处理
     */
    void ClientStoppingHandler();

    /**
     * 客户端线程关闭中遇到错误后处理
     */
    void ClientStoppingErrorHandler();

    /**
     * 客户端线程完全关闭后处理
     */
    void ClientStoppedHandler();

    /**
     * 接收一个参数，实现二维码生成
     *
     * @param qrCodeUrl 二维码URL
     */
    void QrCodeUrlHandler(final String qrCodeUrl);

    /**
     * 将二维码展现出来
     */
    void ShowQrCodeHandler();

    /**
     * 通过二维码，连接成功后的通知
     */
    void ConnectSuccessfulNoticeHandler();

    /**
     * 断开连接信息触发处理
     *
     * @param data 数据
     */
    void DisconnectHandler(final PowerBoxData data);

    /**
     * 错误信息触发处理
     *
     * @param data 数据
     */
    void ErrorHandler(final PowerBoxData data);

    /**
     * 心跳信息触发处理
     *
     * @param data 数据
     */
    void HeartBeatHandler(final PowerBoxData data);

    /**
     * 其它信息触发处理
     *
     * @param data 数据
     */
    void OtherMessageHandler(final PowerBoxData data);
}
