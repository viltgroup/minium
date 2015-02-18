package minium.web.config.services;

import java.io.File;
import java.util.Map;

import org.openqa.selenium.ie.InternetExplorerDriverEngine;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService.Builder;
import org.openqa.selenium.remote.service.DriverService;

public class InternetExplorerDriverServiceProperties extends DriverServiceProperties {
    private File driverExecutable;
    private Integer port;
    private Map<String, String> environment;
    private File logFile;
    private String logLevel;
    private String engineImplementation;
    private String host;
    private File extractPath;
    private Boolean silent;

    public File getDriverExecutable() {
        return driverExecutable;
    }

    public void setDriverExecutable(File driverExecutable) {
        this.driverExecutable = driverExecutable;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, String> environment) {
        this.environment = environment;
    }

    public File getLogFile() {
        return logFile;
    }

    public void setLogFile(File logFile) {
        this.logFile = logFile;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getEngineImplementation() {
        return engineImplementation;
    }

    public void setEngineImplementation(String engineImplementation) {
        this.engineImplementation = engineImplementation;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public File getExtractPath() {
        return extractPath;
    }

    public void setExtractPath(File extractPath) {
        this.extractPath = extractPath;
    }

    public Boolean getSilent() {
        return silent;
    }

    public void setSilent(Boolean silent) {
        this.silent = silent;
    }

    @Override
    public DriverService createDriverService() {
        Builder builder = new InternetExplorerDriverService.Builder();
        if (port != null) builder.usingPort(port);
        if (driverExecutable != null) builder.usingDriverExecutable(driverExecutable);
        if (environment != null) builder.withEnvironment(environment);
        if (logFile != null) builder.withLogFile(logFile);
        if (logLevel != null) builder.withLogLevel(InternetExplorerDriverLogLevel.valueOf(logLevel.toUpperCase()));
        if (engineImplementation != null) builder.withEngineImplementation(InternetExplorerDriverEngine.valueOf(engineImplementation.toUpperCase()));
        if (host != null) builder.withHost(host);
        if (extractPath != null) builder.withExtractPath(extractPath);
        if (silent != null) builder.withSilent(silent);
        return builder.build();
    }
}
