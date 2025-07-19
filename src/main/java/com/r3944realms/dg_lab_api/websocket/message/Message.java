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


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.r3944realms.dg_lab_api.websocket.message.data.IData;
import com.r3944realms.dg_lab_api.websocket.message.data.adapter.IDataTypeAdapterFactory;
import com.r3944realms.dg_lab_api.websocket.message.role.Role;
import com.r3944realms.dg_lab_api.websocket.message.role.RoleDeserializer;

import java.io.Serial;
import java.io.Serializable;

/**
 * 消息，带有方向和有效负载消息
 */
public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * The constant gson.
     */
    @Expose(deserialize = false, serialize = false)
    final static Gson gson;
    /**
     * The Direction.
     */
    final public MessageDirection<?,?> direction;
    /**
     * The Payload.
     */
    final IData payload;
    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Role.class, new RoleDeserializer());
        gsonBuilder.registerTypeAdapter(MessageDirection.class, new MessageDirectionDeserializer());
        gsonBuilder.registerTypeAdapterFactory(new IDataTypeAdapterFactory());
        gson = gsonBuilder.create();
    }

    /**
     * 额外的信息, 如
     * <ul>
     * <li>消息发送者UUID</li>
     * <li>消息创建时间</li>
     * <li>信息校对值</li>
     * <li>...</li>
     * </ul>
     *
     * @return 信息 string
     */
    public abstract String AdditionalInformation();

    /**
     * Instantiates a new Message.
     *
     * @param payload   the payload
     * @param direction the direction
     */
    Message(IData payload, MessageDirection<?, ?> direction) {
        this.payload = payload;
        this.direction = direction;
    }

    /**
     * Gets data json.
     *
     * @return the data json
     */
    public String getDataJson() {
        return getDataJson(false);
    }

    /**
     * 无效信息返回
     *
     * @return Json invalid message json
     */
    public abstract String getInvalidMessageJson();

    /**
     * Gets data json.
     *
     * @param isFix the is fix
     * @return the data json
     */
    public String getDataJson(boolean isFix) {
        if(payload == null) return getInvalidMessageJson();
        return payload.isValid() ?
                (isFix ? gson.toJson(payload).replace("\\","") : gson.toJson(payload))
                : getInvalidMessageJson();
    }

    /**
     * Gets msg json.
     *
     * @return the msg json
     */
    public String getMsgJson() {
        return getMsgJson(false);
    }

    /**
     * Gets msg json.
     *
     * @param isFix the is fix
     * @return the msg json
     */
    public String getMsgJson(boolean isFix) {
        if(payload == null && direction == null) return getInvalidMessageJson();
        return isFix ? gson.toJson(this).replace("\\","") : gson.toJson(this);
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public IData getPayload() {
        return this.payload;
    }

    /**
     * Read json return message message.
     *
     * @param dataJson         the data json
     * @param messageDirection the message direction
     * @return the message
     */
    public abstract Message readJsonReturnMessage(String dataJson, MessageDirection<?, ?> messageDirection);

    /**
     * Gets payload.
     *
     * @param json the json
     * @return the payload
     */
    public abstract IData getPayload(String json);

    /**
     * Gets message.
     *
     * @param json the json
     * @return the message
     */
    public abstract Message getMessage(String json);
}
