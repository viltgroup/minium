package minium.web.config.services;

import java.io.File;

import org.openqa.selenium.Platform;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;


public class DriverServicesProperties implements DisposableBean {

    @Value("${app.home:.}")
    private File homedir;
    private ChromeDriverServiceProperties chrome;
    private InternetExplorerDriverServiceProperties internetExplorer;
    private PhantomJsDriverServiceProperties phantomJs;

    public ChromeDriverServiceProperties getChrome() {
        if (chrome == null) {
            File chromedriverExe = findExecutable("chromedriver");
            if (chromedriverExe != null) {
                chrome = new ChromeDriverServiceProperties();
                chrome.setDriverExecutable(chromedriverExe);
                chrome.setSilent(true);
            }
        }
        return chrome;
    }

    public void setChrome(ChromeDriverServiceProperties chrome) {
        this.chrome = chrome;
    }

    public InternetExplorerDriverServiceProperties getInternetExplorer() {
        if (internetExplorer == null) {
            File ieDriverExe = findExecutable("IEDriverServer");
            if (ieDriverExe != null) {
                internetExplorer = new InternetExplorerDriverServiceProperties();
                internetExplorer.setDriverExecutable(ieDriverExe);
            }
        }
        return internetExplorer;
    }

    public void setInternetExplorer(InternetExplorerDriverServiceProperties internetExplorer) {
        this.internetExplorer = internetExplorer;
    }

    public PhantomJsDriverServiceProperties getPhantomJs() {
        if (phantomJs == null) {
            File phantomjsExe = findExecutable("phantomjs");
            if (phantomjsExe != null) {
                phantomJs = new PhantomJsDriverServiceProperties();
                phantomJs.setDriverExecutable(phantomjsExe);
            }
        }
        return phantomJs;
    }

    public void setPhantomJs(PhantomJsDriverServiceProperties phamtomJs) {
        this.phantomJs = phamtomJs;
    }

    protected File findExecutable(String exeName) {
        File driverDir = getDriverDir();
        if (driverDir == null) return null;

        String osSpecificExeName = Platform.getCurrent().is(Platform.WINDOWS) ? exeName + ".exe" : exeName;
        File exeFile = new File(driverDir, osSpecificExeName);

        return exeFile.exists() && exeFile.isFile() && exeFile.canExecute() ? exeFile : null;
    }

    protected File getDriverDir() {
        File driverDir = homedir == null ? null : new File(homedir, "drivers");
        return driverDir != null && driverDir.exists() && driverDir.isDirectory() ? driverDir : null;
    }

    @Override
    public void destroy() throws Exception {
        if (chrome != null) chrome.destroy();
        if (internetExplorer != null) internetExplorer.destroy();
        if (phantomJs != null) phantomJs.destroy();
    }
}
