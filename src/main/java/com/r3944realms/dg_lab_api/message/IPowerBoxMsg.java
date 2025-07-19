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

package com.r3944realms.dg_lab_api.message;

import com.r3944realms.dg_lab_api.dataType.PowerBoxCommands;
import com.r3944realms.dg_lab_api.dataType.PowerBoxMsgType;
import com.r3944realms.dg_lab_api.message.argType.ChangePolicy;
import com.r3944realms.dg_lab_api.message.argType.Channel;
import com.r3944realms.dg_lab_api.message.data.PulseWaveList;
import com.r3944realms.dg_lab_api.message.data.PulseWaveListGenerator;
import com.r3944realms.dg_lab_api.exception.NoMatchDataTypeException;
import com.r3944realms.dg_lab_api.websocket.message.MessageDirection;
import com.r3944realms.dg_lab_api.websocket.message.PowerBoxMessage;
import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxDataWithSingleAttachment;
import com.r3944realms.dg_lab_api.websocket.message.data.PowerBoxData;
import com.r3944realms.dg_lab_api.websocket.message.data.type.PowerBoxDataType;
import com.r3944realms.dg_lab_api.websocket.message.role.Role;

import java.util.Arrays;

/**
 * The interface Power box msg.
 */
public interface IPowerBoxMsg {

    /**
     * To power box data power box data.
     *
     * @param clientUUID the client uuid
     * @param targetUUID the target uuid
     * @return the power box data
     */
    PowerBoxData toPowerBoxData(String clientUUID, String targetUUID);

    /**
     * To power box message power box message.
     *
     * @param clientUUID the client uuid
     * @param targetUUID the target uuid
     * @param sender     the sender
     * @param receiver   the receiver
     * @return the power box message
     */
    default PowerBoxMessage toPowerBoxMessage(String clientUUID, String targetUUID, Role sender, Role receiver) {
        return new PowerBoxMessage(toPowerBoxData(clientUUID, targetUUID), new MessageDirection<>(sender, receiver));
    }

    /**
     * To power box message power box message.
     *
     * @param clientUUID the client uuid
     * @param targetUUID the target uuid
     * @param direction  the direction
     * @return the power box message
     */
    default PowerBoxMessage toPowerBoxMessage(String clientUUID, String targetUUID, MessageDirection<?, ?> direction) {
        return new PowerBoxMessage(toPowerBoxData(clientUUID, targetUUID), direction);
    }

    /**
     * To power box message power box message.
     *
     * @param clientUUID the client uuid
     * @param targetUUID the target uuid
     * @param type       the type
     * @return the power box message
     */
    default PowerBoxMessage toPowerBoxMessage(String clientUUID, String targetUUID, MessageDirection.DirectType type) {
        return new PowerBoxMessage(toPowerBoxData(clientUUID, targetUUID), MessageDirection.of(type, clientUUID, targetUUID));
    }

    /**
     * To power box message power box message.
     *
     * @param clientUUID   the client uuid
     * @param targetUUID   the target uuid
     * @param receiverName the receiver name
     * @param type         the type
     * @return the power box message
     */
    default PowerBoxMessage toPowerBoxMessage(String clientUUID, String targetUUID, String receiverName, MessageDirection.DirectType type) {
        return new PowerBoxMessage(toPowerBoxData(clientUUID, targetUUID), MessageDirection.of(type, clientUUID, receiverName));
    }

    /**
     * To power box message power box message.
     *
     * @param clientUUID   the client uuid
     * @param targetUUID   the target uuid
     * @param senderName   the sender name
     * @param receiverName the receiver name
     * @param type         the type
     * @return the power box message
     */
    default PowerBoxMessage toPowerBoxMessage(String clientUUID, String targetUUID,String senderName, String receiverName, MessageDirection.DirectType type) {
        return new PowerBoxMessage(toPowerBoxData(clientUUID, targetUUID), MessageDirection.of(type, senderName, receiverName));
    }

    /**
     * The type Strength.
     */
    record StrengthChange(Channel channel, ChangePolicy policy, int value) implements IPowerBoxMsg {
        @Override
        public PowerBoxData toPowerBoxData(String clientUUID, String targetUUID) {
            String msg = PowerBoxCommands.STRENGTH + "-" + channel.index_int + "+" + policy.index + "+" + value;
            return new PowerBoxData(PowerBoxMsgType.MSG_COMMAND, clientUUID, targetUUID, msg);
        }

        /**
         * Read strength change.
         *
         * @param msg the msg
         * @return the strength change
         * @throws NoMatchDataTypeException the no match data type exception
         */
        public static StrengthChange read(PowerBoxMessage msg) throws NoMatchDataTypeException {
            if (msg.commandType != PowerBoxDataType.STRENGTH) throw new NoMatchDataTypeException();
            PowerBoxData payload = msg.getPayload();
            Object[] argsArrayByPointing = payload.getArgsArrayByPointing(msg.commandType);
            if (argsArrayByPointing.length != 3) throw new NoMatchDataTypeException();
            return new StrengthChange(Channel.getChannel((Integer) argsArrayByPointing[0]), ChangePolicy.getChangePolicy((Integer) argsArrayByPointing[1]), (Integer) argsArrayByPointing[2]);
        }
    }

    /**
     * The type Strength info.
     */
    record StrengthInfo(int aValue, int bValue, int aMax, int bMax) implements IPowerBoxMsg {
        @Override
        public PowerBoxData toPowerBoxData(String clientUUID, String targetUUID) {
            String msg = PowerBoxCommands.STRENGTH + "-" + aValue + "+" + bValue + "+" + aMax + "+" + bMax;
            return new PowerBoxData(PowerBoxMsgType.MSG_COMMAND, clientUUID, targetUUID, msg);
        }

        /**
         * Read strength info.
         *
         * @param msg the msg
         * @return the strength info
         * @throws NoMatchDataTypeException the no match data type exception
         */
        public static StrengthInfo read(PowerBoxMessage msg) throws NoMatchDataTypeException {
            if (msg.commandType != PowerBoxDataType.STRENGTH) throw new NoMatchDataTypeException();
            PowerBoxData payload = msg.getPayload();
            Object[] argsArrayByPointing = payload.getArgsArrayByPointing(msg.commandType);
            if (argsArrayByPointing.length != 4) throw new NoMatchDataTypeException();
            return new StrengthInfo((Integer) argsArrayByPointing[0], (Integer) argsArrayByPointing[1], (Integer) argsArrayByPointing[2], (Integer) argsArrayByPointing[3]);
        }
    }

    /**
     * The type Clear.
     */
    record Clear(Channel channel) implements IPowerBoxMsg {
        @Override
        public PowerBoxData toPowerBoxData(String clientUUID, String targetUUID) {
            String msg = PowerBoxCommands.CLEAR + "-" + channel.index_int;
            return new PowerBoxData(PowerBoxMsgType.MSG_COMMAND, clientUUID, targetUUID, msg);
        }

        /**
         * Read clear.
         *
         * @param msg the msg
         * @return the clear
         * @throws NoMatchDataTypeException the no match data type exception
         */
        public static Clear read(PowerBoxMessage msg) throws NoMatchDataTypeException {
            if (msg.commandType != PowerBoxDataType.CLEAR) throw new NoMatchDataTypeException();
            PowerBoxData payload = msg.getPayload();
            Object[] argsArrayByPointing = payload.getArgsArrayByPointing(msg.commandType);
            return new Clear(Channel.getChannel((Integer) argsArrayByPointing[0]));
        }
    }

    /**
     * The type Feedback.
     */
    record Feedback(int feedback) implements IPowerBoxMsg {
        @Override
        public PowerBoxData toPowerBoxData(String clientUUID, String targetUUID) {
            String msg = "feedback" + "-" + feedback;
            return new PowerBoxData(PowerBoxMsgType.MSG_COMMAND, clientUUID, targetUUID, msg);
        }

        /**
         * Read feedback.
         *
         * @param msg the msg
         * @return the feedback
         * @throws NoMatchDataTypeException the no match data type exception
         */
        public static Feedback read(PowerBoxMessage msg) throws NoMatchDataTypeException {
            if (msg.commandType != PowerBoxDataType.FEEDBACK) throw new NoMatchDataTypeException();
            PowerBoxData payload = msg.getPayload();
            Object[] argsArrayByPointing = payload.getArgsArrayByPointing(msg.commandType);
            return new Feedback((Integer) argsArrayByPointing[0]);
        }
    }

    /**
     * The type Pulse.
     */
    record Pulse(Channel channel, PulseWaveList pulseWaveList, Integer timer) implements IPowerBoxMsg {
        /**
         * Instantiates a new Pulse.
         *
         * @param channel  the channel
         * @param waveData the wave data
         * @param timer    the timer
         */
        public Pulse(Channel channel, String[] waveData, Integer timer) {
            this(channel, PulseWaveListGenerator.toPulseWaveListFromStringArray(waveData), timer);
        }

        /**
         * Instantiates a new Pulse.
         *
         * @param channel  the channel
         * @param waveData the wave data
         */
        public Pulse(Channel channel, String[] waveData) {
            this(channel, waveData, 0);
        }

        /**
         * Instantiates a new Pulse.
         *
         * @param channel  the channel
         * @param waveData the wave data
         */
        public Pulse(Channel channel, PulseWaveList waveData) {
            this(channel, waveData, 0);
        }

        @Override
        public PowerBoxDataWithSingleAttachment toPowerBoxData(String clientUUID, String targetUUID) {
            String msg = "pulse-" + channel.index_char + ":" + pulseWaveList.toListString();
            return new PowerBoxData(PowerBoxMsgType.CLIENT_MSG, clientUUID, targetUUID, msg).withSingleAttachment(timer);
        }

        /**
         * Read pulse.
         *
         * @param msg the msg
         * @return the pulse
         * @throws NoMatchDataTypeException the no match data type exception
         */
        public static Pulse read(PowerBoxMessage msg) throws NoMatchDataTypeException {
            PowerBoxData payload = msg.getPayload();
            PowerBoxDataType commandType = payload.getCommandType(true);
            if (commandType != PowerBoxDataType.PULSE) throw new NoMatchDataTypeException();
            Object[] argsArrayByPointing = payload.getArgsArrayByPointing(commandType);//10 0 1 ~ 9
            return new Pulse(Channel.getChannel(((String)argsArrayByPointing[0]).charAt(0)), Arrays.copyOfRange((String[])argsArrayByPointing, 1, argsArrayByPointing.length - 1));
        }
    }
}
