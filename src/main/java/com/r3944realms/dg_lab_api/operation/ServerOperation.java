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

import com.r3944realms.dg_lab_api.websocket.message.PowerBoxMessage;
import org.jetbrains.annotations.Nullable;

/**
 * The interface Server operation.
 */
public interface ServerOperation extends IOperation {

    /**
     * 服务器线程开启中通知
     */
    void ServerStartingHandler();

    /**
     * 服务器线程开启中遇到错误后处理
     */
    void ServerStartingErrorHandler();

    /**
     * 服务器线程开启后处理
     */
    void ServerStartedHandler();

    /**
     * 服务器线程关闭中处理后通知
     */
    void ServerStoppingHandler();

    /**
     * 服务器线程关闭中遇到错误后处理
     */
    void ServerStoppingErrorHandler();

    /**
     * 服务器线程完全关闭后处理
     */
    void ServerStoppedHandler();

    /**
     * 在定时器里即将被移除的UUID处理
     *
     * @param clientId 不活跃的UUID
     */
    void InactiveConnectionRemoveHandler(final String clientId);

    /**
     * 读取信息错误走这里处理
     *
     * @param message 异常消息
     */
    void ErrorMessageHandler(final PowerBoxMessage message);

    /**
     * 绑定成功处理
     *
     * @param message 绑定成功消息
     */
    void BindSuccessMessageHandler(final PowerBoxMessage message);

    /**
     * 绑定失败处理
     *
     * @param message 绑定失败消息
     */
    void BindFailureMessageHandler(final PowerBoxMessage message);

    /**
     * 心跳定时器触发处理
     *
     * @param message 心跳消息
     */
    void HeartbeatMessageHandler(final PowerBoxMessage message);

    /**
     * 清理频道消息处理
     *
     * @param message 清理信息
     */
    void ClearMessageHandler(final PowerBoxMessage message);

    /**
     * 反馈消息处理
     *
     * @param message 反馈消息
     */
    void FeedbackMessageHandler(final PowerBoxMessage message);

    /**
     * 强度通知消息处理
     *
     * @param message 强度通知消息
     */
    void StrengthMessageNoticeMessage(final PowerBoxMessage message);

    /**
     * 强度改变消息处理
     *
     * @param message 强度改变消息
     */
    void StrengthMessageChangeHandler(final PowerBoxMessage message);

    /**
     * 波形信息由客户端下发处理
     *
     * @param clearMessage 清理信息
     * @param pulseMessage 波形消息
     */
    default void PulseClientMessageHandler(final PowerBoxMessage clearMessage, final PowerBoxMessage pulseMessage) {
        PulseClientMessageHandler(clearMessage, 500, pulseMessage);
    }

    /**
     * 波形信息由客户端下发处理
     *
     * @param clearMessage 清理信息
     * @param delayTime    延迟时间
     * @param pulseMessage 波形消息
     */
    void PulseClientMessageHandler(@Nullable final PowerBoxMessage clearMessage, final int delayTime, final PowerBoxMessage pulseMessage);

    /**
     * 连接断开消息处理
     *
     * @param message 断开消息
     */
    void BreakConnectMessageHandler(final PowerBoxMessage message);

    /**
     * 其它类型合法消息转发处理
     *
     * @param message 其它合法消息
     */
    void OtherMessageHandler(final PowerBoxMessage message);
}
