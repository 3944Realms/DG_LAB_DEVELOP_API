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

package com.r3944realms.dg_lab_api.message.argType;

/**
 * 修改策略
 */
public enum ChangePolicy {
    /**
     * 增加
     */
    INCREASE(0),
    /**
     * 减少
     */
    DECREASE(1),
    /**
     * 转变
     */
    GOTO(2);
    /**
     * The Index.
     */
    public final int index;

    ChangePolicy(int index) {
        this.index = index;
    }

    /**
     * Gets change policy.
     *
     * @param index the index
     * @return the change policy
     */
    public static ChangePolicy getChangePolicy(int index) {
        return switch (index) {
            case 0 -> INCREASE;
            case 1 -> DECREASE;
            case 2 -> GOTO;
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }
}
