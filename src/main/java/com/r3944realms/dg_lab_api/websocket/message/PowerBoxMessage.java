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

import com.google.gson.JsonSyntaxException;
import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxData;
import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxDataWithAttachment;
import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxDataWithSingleAttachment;
import com.r3944realms.dg_lab_api.websocket.message.data.type.PowerBoxDataType;
import com.r3944realms.dg_lab_api.websocket.message.data.type.PowerBoxStatusCode;
import com.r3944realms.dg_lab_api.websocket.message.role.PlaceholderRole;
import com.r3944realms.dg_lab_api.websocket.message.role.Role;

/**
 * The type Power box message.
 */
public class PowerBoxMessage extends Message {

    /**
     * The Command type.
     */
    public final PowerBoxDataType commandType;
    private static final PowerBoxMessage Null = PowerBoxMessage.createPowerBoxMessage("null","null","null","null", new PlaceholderRole("null"), new PlaceholderRole("null"));
    /**
     * The Invalid message json.
     */
    static final String INVALID_MESSAGE_JSON = gson.toJson(PowerBoxData.createPowerBoxData("error","","",""));

    /**
     * Instantiates a new Power box message.
     *
     * @param payload   the payload
     * @param direction the direction
     */
    public PowerBoxMessage(PowerBoxData payload, MessageDirection<?, ?> direction) {
        super(payload ,direction);
        commandType = payload.getCommandType();
    }

    /**
     * Create power box message power box message.
     *
     * @param type     the type
     * @param clientId the client id
     * @param targetId the target id
     * @param message  the message
     * @param timer    the timer
     * @param sender   the sender
     * @param receiver the receiver
     * @return the power box message
     */
    public static PowerBoxMessage createPowerBoxMessage(
            String type, String clientId, String targetId, String message, Integer timer,
            Role sender, Role receiver
    ) {
        PowerBoxDataWithSingleAttachment data = new PowerBoxDataWithSingleAttachment(PowerBoxData.createPowerBoxData(type, clientId, targetId, message), timer);
        MessageDirection<?,?> direction = new MessageDirection<>(
                sender,
                receiver
        );
        return new PowerBoxMessage(data, direction);
    }

    /**
     * Create power box message power box message.
     *
     * @param type     the type
     * @param clientId the client id
     * @param targetId the target id
     * @param message  the message
     * @param timerA   the timer a
     * @param timerB   the timer b
     * @param sender   the sender
     * @param receiver the receiver
     * @return the power box message
     */
    @SuppressWarnings("deprecation")
    public static PowerBoxMessage createPowerBoxMessage(
            String type, String clientId, String targetId, String message, Integer timerA, Integer timerB,
            Role sender, Role receiver
    ) {
        PowerBoxDataWithAttachment data = new PowerBoxDataWithAttachment(PowerBoxData.createPowerBoxData(type, clientId, targetId, message), timerA, timerB);
        MessageDirection<?,?> direction = new MessageDirection<>(
                sender,
                receiver
        );
        return new PowerBoxMessage(data, direction);
    }

    /**
     * Create power box message power box message.
     *
     * @param payload   the payload
     * @param direction the direction
     * @return the power box message
     */
    public static PowerBoxMessage createPowerBoxMessage(PowerBoxData payload, MessageDirection<?, ?> direction ) {
        return new PowerBoxMessage(payload, direction);
    }

    /**
     * Create power box message power box message.
     *
     * @param type     the type
     * @param clientId the client id
     * @param targetId the target id
     * @param message  the message
     * @param sender   the sender
     * @param receiver the receiver
     * @return the power box message
     */
    public static PowerBoxMessage createPowerBoxMessage(
            String type, String clientId, String targetId, String message,
            Role sender, Role receiver
    ) {
        PowerBoxData data = PowerBoxData.createPowerBoxData(type, clientId, targetId, message);
        MessageDirection<?,?> direction = new MessageDirection<>(
                sender,
                receiver
        );
        return new PowerBoxMessage(data, direction);
    }

    /**
     * Create power box message power box message.
     *
     * @param type       the type
     * @param clientId   the client id
     * @param targetId   the target id
     * @param statusCode the status code
     * @param sender     the sender
     * @param receiver   the receiver
     * @return the power box message
     */
    public static PowerBoxMessage createPowerBoxMessage(
            String type, String clientId, String targetId, PowerBoxStatusCode statusCode,
            Role sender, Role receiver
    ) {
        return createPowerBoxMessage(type, clientId, targetId, statusCode.getCode(), sender, receiver);
    }

    /**
     * 仅供转化用，不可使用与传递
     *
     * @return Null_Message null message
     */
    public static PowerBoxMessage getNullMessage() {
        return Null;
    }
    @Override
    public String AdditionalInformation() {
        return "IPowerBoxMessage : " + direction.toString();
    }

    @Override
    public String getInvalidMessageJson() {
        return INVALID_MESSAGE_JSON;
    }

    @Override
    public PowerBoxMessage readJsonReturnMessage(String dataJson, MessageDirection<?, ?> messageDirection) throws JsonSyntaxException {
        return new PowerBoxMessage(getPayload(dataJson), messageDirection);
    }

    @Override
    public PowerBoxData getPayload() {
        return (PowerBoxData)payload;
    }

    /**
     *
     * @param json PowerBoxDataJSON
     * @return PowerBoxData （如果Data字段存在空则会返回null值）
     */
    @Override
    public PowerBoxData getPayload(String json) throws JsonSyntaxException {
        PowerBoxData powerBoxData = gson.fromJson(json, PowerBoxData.class);
        return (powerBoxData.Type() != null && powerBoxData.getClientId() != null && powerBoxData.getTargetId() != null && powerBoxData.getMessage() != null) ? powerBoxData : null;
    }

    /**
     *
     * @param json PowerBoxMessageJSON
     * @return PowerBoxMessage （如果message字段存在空则会返回null值）
     */
    @Override
    public PowerBoxMessage getMessage(String json) {
        PowerBoxMessage message = gson.fromJson(json, PowerBoxMessage.class);
        return (message.direction != null && message.payload != null && message.commandType != null) ? message : null;
    }

    /**
     * Gets payload with attachment.
     *
     * @param json the json
     * @return the payload with attachment
     * @throws JsonSyntaxException the json syntax exception
     */
    @SuppressWarnings("deprecation")
    public PowerBoxDataWithAttachment getPayloadWithAttachment(String json) throws JsonSyntaxException {
        return gson.fromJson(json, PowerBoxDataWithAttachment.class);
    }

    /**
     * Gets payload with single attachment.
     *
     * @param json the json
     * @return the payload with single attachment
     * @throws JsonSyntaxException the json syntax exception
     */
    public PowerBoxDataWithSingleAttachment getPayloadWithSingleAttachment(String json) throws JsonSyntaxException {
        return gson.fromJson(json, PowerBoxDataWithSingleAttachment.class);
    }

}
