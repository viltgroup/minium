package minium.web.config.services;

import java.io.File;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeDriverService.Builder;
import org.openqa.selenium.remote.service.DriverService;

public class ChromeDriverServiceProperties extends DriverServiceProperties {

    private Integer port;
    private File driverExecutable;
    private Map<String, String> environment;
    private File logFile;
    private Boolean verbose;
    private Boolean silent;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public File getDriverExecutable() {
        return driverExecutable;
    }

    public void setDriverExecutable(File exe) {
        this.driverExecutable = exe;
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

    public Boolean getVerbose() {
        return verbose;
    }

    public void setVerbose(Boolean verbose) {
        this.verbose = verbose;
    }

    public Boolean getSilent() {
        return silent;
    }

    public void setSilent(Boolean silent) {
        this.silent = silent;
    }

    @Override
    protected DriverService createDriverService() {
        Builder builder = new ChromeDriverService.Builder();
        if (port != null) builder.usingPort(port);
        if (driverExecutable != null) builder.usingDriverExecutable(driverExecutable);
        if (environment != null) builder.withEnvironment(environment);
        if (logFile != null) builder.withLogFile(logFile);
        if (verbose != null) builder.withVerbose(verbose);
        if (silent != null) builder.withSilent(silent);
        return builder.build();
    }
}
