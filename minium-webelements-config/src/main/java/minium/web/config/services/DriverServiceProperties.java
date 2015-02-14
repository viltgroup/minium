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
