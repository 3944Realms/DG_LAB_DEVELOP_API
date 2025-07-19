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

public interface IData {

    /**
     * 获取 无效原因
     *
     * @return inValidReason 无效原因
     */
    default String getInvalidReason() {
        return "Invalid arguments [Default Reason]";
    }

    /**
     * 当前数据是否有效
     *
     * @return isValid 有效与否
     */
    boolean isValid();

    /**
     * 获取当前数据类型
     *
     * @return DateType 数据类型
     */
    DataType Type();
}
