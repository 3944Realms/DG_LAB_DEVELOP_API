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

package com.r3944realms.dg_lab_api.websocket.message;

import com.r3944realms.dg_lab_api.websocket.message.role.*;

import java.io.Serializable;

/**
 * {@link Message}的参数之一
 *
 * @param <T> 接收者类型
 * @param <K> 发送者类型
 */
public record MessageDirection<T extends Role, K extends Role>(T sender, K receiver) implements Serializable {

    @Override
    public String toString() {
        return
                "MessageDirection:[ " + sender.type + " -> " + receiver.type + " ] {" + sender.name + " -> " + receiver.name + "}";
    }

    /**
     * Of message direction.
     *
     * @param type         the type
     * @param SenderUUID   the sender uuid
     * @param ReceiverUUID the receiver uuid
     * @return the message direction
     */
    public static MessageDirection<? extends Role, ? extends Role> of(final DirectType type, final String SenderUUID, final String ReceiverUUID) {
        return switch (type) {
            case PLACEHOLDER_TO_PLACEHOLDER -> new MessageDirection<>(new PlaceholderRole(SenderUUID), new PlaceholderRole(ReceiverUUID));
            case PLACEHOLDER_TO_CLIENT -> new MessageDirection<>(new PlaceholderRole(SenderUUID), new WebSocketClientRole(ReceiverUUID));
            case PLACEHOLDER_TO_SERVER -> new MessageDirection<>(new PlaceholderRole(SenderUUID), new WebSocketServerRole(ReceiverUUID));
            case PLACEHOLDER_TO_APPLICATION -> new MessageDirection<>(new PlaceholderRole(SenderUUID), new WebSocketApplicationRole(ReceiverUUID));
            case CLIENT_TO_PLACEHOLDER -> new MessageDirection<>(new WebSocketClientRole(SenderUUID), new PlaceholderRole(ReceiverUUID));
            case CLIENT_TO_CLIENT -> new MessageDirection<>(new WebSocketClientRole(SenderUUID), new WebSocketClientRole(ReceiverUUID));
            case CLIENT_TO_SERVER -> new MessageDirection<>(new WebSocketClientRole(SenderUUID), new WebSocketServerRole(ReceiverUUID));
            case CLIENT_TO_APPLICATION -> new MessageDirection<>(new WebSocketClientRole(SenderUUID), new WebSocketApplicationRole(ReceiverUUID));
            case SERVER_TO_PLACEHOLDER -> new MessageDirection<>(new WebSocketServerRole(SenderUUID), new PlaceholderRole(ReceiverUUID));
            case SERVER_TO_CLIENT -> new MessageDirection<>(new WebSocketServerRole(SenderUUID), new WebSocketClientRole(ReceiverUUID));
            case SERVER_TO_APPLICATION -> new MessageDirection<>(new WebSocketServerRole(SenderUUID), new WebSocketApplicationRole(ReceiverUUID));
            case SERVER_TO_SERVER -> new MessageDirection<>(new WebSocketServerRole(SenderUUID), new WebSocketServerRole(ReceiverUUID));
            case APPLICATION_TO_PLACEHOLDER -> new MessageDirection<>(new WebSocketApplicationRole(SenderUUID), new PlaceholderRole(ReceiverUUID));
            case APPLICATION_TO_CLIENT -> new MessageDirection<>(new WebSocketApplicationRole(SenderUUID), new WebSocketClientRole(ReceiverUUID));
            case APPLICATION_TO_APPLICATION -> new MessageDirection<>(new WebSocketApplicationRole(SenderUUID), new WebSocketApplicationRole(ReceiverUUID));
            case APPLICATION_TO_SERVER -> new MessageDirection<>(new WebSocketApplicationRole(SenderUUID), new WebSocketServerRole(ReceiverUUID));
        };
    }

    /**
     * The enum Direct type.
     */
    public enum DirectType {
        /**
         * Placeholder to placeholder direct type.
         */
        PLACEHOLDER_TO_PLACEHOLDER,
        /**
         * Placeholder to client direct type.
         */
        PLACEHOLDER_TO_CLIENT,
        /**
         * Placeholder to server direct type.
         */
        PLACEHOLDER_TO_SERVER,
        /**
         * Placeholder to application direct type.
         */
        PLACEHOLDER_TO_APPLICATION,
        /**
         * Client to placeholder direct type.
         */
        CLIENT_TO_PLACEHOLDER,
        /**
         * Client to client direct type.
         */
        CLIENT_TO_CLIENT,
        /**
         * Client to server direct type.
         */
        CLIENT_TO_SERVER,
        /**
         * Client to application direct type.
         */
        CLIENT_TO_APPLICATION,
        /**
         * Server to placeholder direct type.
         */
        SERVER_TO_PLACEHOLDER,
        /**
         * Server to client direct type.
         */
        SERVER_TO_CLIENT,
        /**
         * Server to application direct type.
         */
        SERVER_TO_APPLICATION,
        /**
         * Server to server direct type.
         */
        SERVER_TO_SERVER,
        /**
         * Application to placeholder direct type.
         */
        APPLICATION_TO_PLACEHOLDER,
        /**
         * Application to client direct type.
         */
        APPLICATION_TO_CLIENT,
        /**
         * Application to application direct type.
         */
        APPLICATION_TO_APPLICATION,
        /**
         * Application to server direct type.
         */
        APPLICATION_TO_SERVER;
    }
}
