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

package com.r3944realms.dg_lab_api.websocket.message.data;

import com.r3944realms.dg_lab_api.websocket.message.data.type.PowerBoxDataType;
import com.r3944realms.dg_lab_api.websocket.message.data.type.PowerBoxStatusCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PowerBox 负载消息数据
 */
@SuppressWarnings("FieldCanBeLocal")
public class PowerBoxData implements IData {
    private final String type;
    private final String clientId;
    private final String targetId;
    private final String message;
    AtomicReference<String> inValidReason = new AtomicReference<>(getInvalidReason());
    /**
     * Instantiates a new Power box data.
     *
     * @param type     the type
     * @param clientId the client id
     * @param targetId the target id
     * @param message  the message
     */
    public PowerBoxData(String type, String clientId, String targetId, String message) {
        this.type = type;
        this.clientId = clientId;
        this.targetId = targetId;
        this.message = message;
    }

    /**
     * Create power box data power box data.
     *
     * @param type     the type
     * @param clientId the client id
     * @param targetId the target id
     * @param message  the message
     * @return the power box data
     */
    public static PowerBoxData createPowerBoxData(String type, String clientId, String targetId, String message) {
        return new PowerBoxData(type, clientId, targetId, message);
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Gets target id.
     *
     * @return the target id
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * With single attachment power box data with single attachment.
     *
     * @param timer the timer
     * @return the power box data with single attachment
     */
    public PowerBoxDataWithSingleAttachment withSingleAttachment(Integer timer) {
        return new PowerBoxDataWithSingleAttachment(this, timer);
    }

    /**
     * With attachment power box data with attachment.
     *
     * @param timerA the timer a
     * @param timerB the timer b
     * @return the power box data with attachment
     */
    @SuppressWarnings("deprecation")
    public PowerBoxDataWithAttachment withAttachment(Integer timerA, Integer timerB) {
        return new PowerBoxDataWithAttachment(this, timerA, timerB);
    }

    @Override
    public boolean isValid() {
        if(type == null || type.isEmpty() || clientId == null  || targetId == null || message == null) {
            inValidReason.set("Invalid PowerBox Data");
            return false;
        }
        final boolean commonValidCheck = !clientId.isEmpty() && !targetId.isEmpty() && !message.isEmpty();
        return switch (type) {
            case "heartbeat" -> !clientId.isEmpty() && PowerBoxStatusCode.isValidStatusCode(message);
            case "bind" -> Objects.equals(message, "targetId") ? (targetId.isEmpty() && !clientId.isEmpty()) : commonValidCheck;
            case "msg" -> !clientId.isEmpty() && !targetId.isEmpty() && isCommandValid(message);
            case "break","clientMsg" -> commonValidCheck;
            case "error" -> !message.isEmpty();
            default -> false;
        };
    }

    @Override
    public DataType Type() {
        return DataType.POWER_BOX;
    }

    /**
     * Gets command type.
     *
     * @return the command type
     */
    public PowerBoxDataType getCommandType() {
        return PowerBoxDataType.getType(type, message);
    }

    /**
     * Gets command type.
     *
     * @param mayPulse the may pulse
     * @return the command type
     */
    public PowerBoxDataType getCommandType(boolean mayPulse) {
        return PowerBoxDataType.getType(type, message, mayPulse);
    }

    /**
     * Get args array by pointing object [ ].
     *
     * @param dataType the data type
     * @return the object [ ]
     */
    @SuppressWarnings("DuplicatedCode")
    public Object[] getArgsArrayByPointing(PowerBoxDataType dataType) {
        if(message == null || message.isEmpty()) {
            return null;
        }
        String[] args = message.split("-");
        switch(dataType) {
            case STRENGTH: {
                String[] arguments = args[1].split("\\+");
                int argumentsLength = arguments.length;
                switch (argumentsLength) {
                    case 3:{
                        int channel = Integer.parseInt(arguments[0]);
                        int strengthChangePolicy = Integer.parseInt(arguments[1]);
                        int value = Integer.parseInt(arguments[2]);
                        return new Integer[]{channel, strengthChangePolicy, value};
                    }
                    case 4:{
                        int AStrength = Integer.parseInt(arguments[0]);
                        int BStrength = Integer.parseInt(arguments[1]);
                        int ALimit = Integer.parseInt(arguments[2]);
                        int BLimit = Integer.parseInt(arguments[3]);
                        return new Integer[]{AStrength, BStrength, ALimit, BLimit};
                    }
                }
            }
            case PULSE: {
                String channel = args[1].substring(0,1);
                String[] DataList = getWaveformDataList(args[1]);
                String[] dataList = new String[DataList.length + 1];
                int i = 0;
                dataList[i] = channel;
                for(String str : DataList) {
                    i++;
                    dataList[i] = str;
                }
                return dataList;

            }
            case CLEAR: {
                return new Integer[]{ Integer.parseInt(args[1]) };
            }
            case FEEDBACK:{
                int arg = Integer.parseInt(args[1]);
                return new Integer[]{ arg };
            }
            default: return null;
        }
    }

    /**
     * Get args array object [ ].
     *
     * @return the object [ ]
     */
    @SuppressWarnings("DuplicatedCode")
    public Object[] getArgsArray() {
        if(message == null || message.isEmpty()) {
            return null;
        }
        String[] args = message.split("-");
        switch(args[0]) {
            /* 强度 */
            case "strength": {
                String[] arguments = args[1].split("\\+");
                int argumentsLength = arguments.length;
                switch (argumentsLength) {
                    /* 这个是客户端->指令中转服务器->App->PowerBox主机 */
                    case 3:{
                        int channel = Integer.parseInt(arguments[0]);
                        int strengthChangePolicy = Integer.parseInt(arguments[1]);
                        int value = Integer.parseInt(arguments[2]);
                        /*=== 通道{1->A, 2->B} + 策略模式{0-减小, 1-增加 ,2-指定} + 数值 ===*/
                        return new Integer[]{channel, strengthChangePolicy, value};
                    }
                    /* 这个是PowerBox主机->App->指令中转服务器->客户端 */
                    case 4:{
                        int AStrength = Integer.parseInt(arguments[0]);
                        int BStrength = Integer.parseInt(arguments[1]);
                        int ALimit = Integer.parseInt(arguments[2]);
                        int BLimit = Integer.parseInt(arguments[3]);
                        /*=== A通道目前强度 + B通道目前强度 + A通道强度上限 + B通道强度上限 ===*/
                        return new Integer[]{AStrength, BStrength, ALimit, BLimit};
                    }
                }
            }
            /* 波形 */
            case "pulse": {
                String channel = args[1].substring(0,1);
                String[] DataList = getWaveformDataList(args[1]);
                String[] dataList = new String[DataList.length + 1];
                int i = 0;
                /* 频道 {A->A, B->B} */
                dataList[i] = channel;
                for(String str : DataList) {
                    i++;
                    /* 一段波形数据如 1122334455667788 */
                    /* 解释为：
                    *    第0~25ms频率,第25~50ms频率, 第50~75ms频率, 第75~100ms频率:  0x11, 0x22, 0x33 0x44
                    *    第0~25ms强度,第25~50ms强度, 第50~75ms强度, 第75~100ms强度:  0x55, 0x66, 0x77 0x88
                    * */
                    dataList[i] = str;
                }
                return dataList;

            }
            /* 清空波形 */
            case "clear": {
                /* 通道{1->A, 2->B} */
                return new Integer[]{ Integer.parseInt(args[1]) };
            }
            /* 反馈 */
            case "feedback":{
                /* 拟定不同形状图标代表的感受状态 */
                return new Integer[]{ Integer.parseInt(args[1]) };
            }
            default: return null;
        }
    }

    /**
     * Is command valid boolean.
     *
     * @param command the command
     * @return the boolean
     */
    public boolean isCommandValid(String command) {
        if(command == null || command.isEmpty()) {
            return false;
        }
        String[] args = command.split("-");
        try {
            switch(args[0]) {
                case "strength": {
                    String[] arguments = args[1].split("\\+");
                    int argumentsLength = arguments.length;
                    switch (argumentsLength) {
                        case 3:{
                            int channel = Integer.parseInt(arguments[0]);
                            if(channel != 1 && channel != 2) throw new IllegalArgumentException("Channel must be 1 or 2");
                            int strengthChangePolicy = Integer.parseInt(arguments[1]);
                            if(2 < strengthChangePolicy || strengthChangePolicy < 0) throw new IllegalArgumentException("Strength change policy must in the range of [0,2]");
                            int value = Integer.parseInt(arguments[2]);
                            if (value < 0 || value > 200) throw new IllegalArgumentException("Value must be between 0 and 200");
                            return true;
                        }
                        case 4:{
                            return true;//App发来的数据应该不会有问题（如果有也不是我的锅（））
                        }
                        default: throw new IllegalArgumentException("Invalid number of arguments");
                    }
                }
                case "pulse": {
                    String channel = args[1].substring(0,1);
                    if(!(channel.equals("A") || channel.equals("B"))) throw new IllegalArgumentException("Channel is incorrect or lacked.");
                    String[] DataList = getWaveformDataList(args[1]);
                    Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{16}$");//检查是否为16进制数字（大小写都可以）
                    if (DataList.length > 100) throw new IllegalArgumentException("The list of Waveform data is too long.");
                    for(String str : DataList) {
                        if(str.length() != 16) {
                            throw new IllegalArgumentException("Find list has a the invalid length of waveform data.");
                        }
                        Matcher matcher = pattern.matcher(str);
                        if (!matcher.matches()) {
                            throw new NumberFormatException("Find list has a incorrect syntax of waveform data.");
                        }
                    }
                    return true;
                }
                case "clear": {
                    String arg = args[1];
                    if(args.length != 2) throw new IllegalArgumentException("Invalid number of arguments");
                    if(!Objects.equals(arg, "1") && !Objects.equals(arg, "2")) throw new IllegalArgumentException("The argument must be 1 or 2");
                    return true;
                }
                case "feedback":{
                    int arg = Integer.parseInt(args[1]);
                    if(args.length != 2) throw new IllegalArgumentException("Invalid number of arguments");
                    if(0 > arg || arg > 10) throw new IllegalArgumentException("args must be between 0 and 10");
                    return true;
                }
                default: throw new IllegalArgumentException("Invalid command");
            }
        } catch (Exception e) {
            inValidReason.set(e.getMessage());
            return false;//指令不正确，直接否
        }
    }

    /**
     * Get waveform data list string [ ].
     *
     * @param msg the msg
     * @return the string [ ]
     */
    String[] getWaveformDataList(String msg) {
        String dataList = msg.substring(msg.indexOf('[') + 1, msg.indexOf(']'));
        String[] rawStringList = dataList.split(",");
        ArrayList<String> list = new ArrayList<>();
        Arrays.stream(rawStringList).forEach(rawString -> {
            list.add(rawString.replaceAll("\"", ""));
        });
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }

}
