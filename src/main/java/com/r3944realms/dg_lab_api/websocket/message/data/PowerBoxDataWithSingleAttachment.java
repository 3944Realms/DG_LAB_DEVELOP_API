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
 * PowerBox单附加的额外数据
 */
public class PowerBoxDataWithSingleAttachment extends PowerBoxData {
    @Nullable
    private final Integer timer;

    /**
     * Instantiates a new Power box data with single attachment.
     *
     * @param parent the parent
     * @param timer  the timer
     */
    public PowerBoxDataWithSingleAttachment(PowerBoxData parent,@Nullable Integer timer) {
        super(parent.getType(), parent.getClientId(), parent.getTargetId(), parent.getMessage());
        this.timer = timer;
    }

    /**
     * Gets timer.
     *
     * @return the timer
     */
    @Nullable
    public Integer getTimer() {
        return timer;
    }

    /**
     * Attach power box data with single attachment.
     *
     * @param parent the parent
     * @param timer  the timer
     * @return the power box data with single attachment
     */
    public static PowerBoxDataWithSingleAttachment attach(PowerBoxData parent, Integer timer){
        if(parent == null)
            throw new NullPointerException("parent is null");
        return new PowerBoxDataWithSingleAttachment(parent, timer);
    }
    @Override
    public boolean isValid() {
        return super.isValid();
    }
    @Override
    public DataType Type() {
        return DataType.DEFAULT;
    }
}
