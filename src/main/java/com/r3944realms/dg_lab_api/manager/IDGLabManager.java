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

package com.r3944realms.dg_lab_api.manager;

import com.r3944realms.dg_lab_api.websocket.sharedData.ISharedData;

/**
 * DGLab管理接口
 */
public interface IDGLabManager {

    /**
     * Start.
     */
    void start();

    /**
     * Stop.
     */
    void stop();

    /**
     * Gets shared data.
     *
     * @return the shared data
     */
    ISharedData getSharedData();
    /**
     * 获取当前单例实例运行
     *
     * @return 运行状态 status
     */
    Status getStatus();

    /**
     * 设置当前单例实例运行
     *
     * @param status 运行状态
     */
    void setStatus(Status status);

}
