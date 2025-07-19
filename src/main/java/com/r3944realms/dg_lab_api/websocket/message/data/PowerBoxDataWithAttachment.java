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

import org.jetbrains.annotations.Nullable;

/**
 * PowerBox双附加的额外数据
 *
 * @deprecated 建议使用单附加的
 */
@Deprecated
public class PowerBoxDataWithAttachment extends PowerBoxData {
    @Nullable
    private final Integer timer_A;
    @Nullable
    private final Integer timer_B;

    /**
     * Instantiates a new Power box data with attachment.
     *
     * @param parent  the parent
     * @param timer_A the timer a
     * @param timer_B the timer b
     */
    public PowerBoxDataWithAttachment(PowerBoxData parent,@Nullable Integer timer_A,@Nullable Integer timer_B) {
        super(parent.getType(), parent.getClientId(), parent.getTargetId(), parent.getMessage());
        this.timer_A = timer_A;
        this.timer_B = timer_B;
    }

    /**
     * Attach power box data with attachment.
     *
     * @param parent  the parent
     * @param timer_A the timer a
     * @param timer_B the timer b
     * @return the power box data with attachment
     */
    public static PowerBoxDataWithAttachment attach(PowerBoxData parent, Integer timer_A, Integer timer_B) {
        if(parent == null)
            throw new NullPointerException("parent is null");
        return new PowerBoxDataWithAttachment(parent, timer_A, timer_B);
    }

    /**
     * Gets timer a.
     *
     * @return the timer a
     */
    @Nullable
    public Integer getTimerA() {
        return timer_A;
    }

    /**
     * Gets timer b.
     *
     * @return the timer b
     */
    @Nullable
    public Integer getTimerB() {
        return timer_B;
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    @Override
    public DataType Type() {
        return DataType.POWER_BOX_ATT;
    }
}
