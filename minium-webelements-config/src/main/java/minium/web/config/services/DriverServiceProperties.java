/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.web.config.services;

import java.io.IOException;

import org.openqa.selenium.remote.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import com.google.common.base.Throwables;

public abstract class DriverServiceProperties implements DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverServiceProperties.class);

    private DriverService driverService;
    private boolean wasRunning;

    public final DriverService getDriverService() {
        try {
            if (driverService == null) {
                driverService = createDriverService();
                wasRunning = driverService.isRunning();
                if (wasRunning) {
                    LOGGER.info("Driver service {} is already running", driverService.getUrl());
                } else {
                    driverService.start();
                    LOGGER.info("Driver service {} started", driverService.getUrl());
                }
            }
            return driverService;
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    protected abstract DriverService createDriverService();

    @Override
    public void destroy() throws Exception {
        if (driverService != null) {
            if (wasRunning) {
                LOGGER.info("Driver service {} was already running, so we don't stop it", driverService.getUrl());
            } else {
                driverService.stop();
                LOGGER.info("Driver service {} stopped", driverService.getUrl());
            }
        }
    }
}
