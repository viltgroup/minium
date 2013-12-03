package com.vilt.minium.app;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;

import java.io.File;
import java.io.IOException;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.webapp.WebAppContext;

import com.google.common.base.Strings;
import com.vilt.minium.prefs.AppPreferences;
import com.vilt.minium.prefs.WebConsolePreferences;

public class Main {

    private static final String MINIUM_HOME_KEY = "minium.home";
	
	private AppPreferences appPreferences;
	private WebConsolePreferences webConsolePreferences;
	
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.createServer();
	}
	
	public Main() throws IOException {
       validateAndLoadConfiguration();
	   System.out.println(format("Using Minium base dir: %s", appPreferences.getBaseDir()));
    }

    public void createServer() throws Exception, InterruptedException {
        final Server server = new Server();
		
		Connector connector = new SelectChannelConnector();
		connector.setHost(webConsolePreferences.getHost());
		connector.setPort(webConsolePreferences.getPort());
		server.addConnector(connector);
		
		server.setStopAtShutdown(true);
		server.setGracefulShutdown(webConsolePreferences.getShutdownPort());
		
		final File baseDir = appPreferences.getBaseDir();
		
        File webappDir = new File(baseDir, "webapp");
		WebAppContext context = new WebAppContext();
        context.setContextPath("/minium-webconsole");
        context.setWar(webappDir.getAbsolutePath());

        server.setHandler(context);
        
        server.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
        	@Override
        	public void lifeCycleStarted(LifeCycle event) {
        		EmbeddedBrowser browser = new EmbeddedBrowser(baseDir, webConsolePreferences, new EmbeddedBrowser.Listener() {
        			@Override
        			public void closed() {
        				try {
        					server.stop();
        				} catch (Exception e) {
        					throw new RuntimeException(e);
        				}
        			}
        		});
        		browser.start();
        	}
		});
        
		server.start();
		server.join();
    }

    private void validateAndLoadConfiguration() throws IOException {
        String path = System.getProperty(MINIUM_HOME_KEY);
        checkNotNull(path, "System property %s is not defined. please ensure that you run Minium App with -D%s=<minium install folder>", MINIUM_HOME_KEY);
        
        File file = new File(path).getAbsoluteFile();
        checkState(file.exists(), "Path %s does not exist", path);
        checkState(file.isDirectory(), "Path %s is not a directory", path);
        
        File configurationFile = new File(path, "minium-prefs.json");
        
        checkState(configurationFile.exists() , "Configuration file %s does not exist", configurationFile);
        checkState(configurationFile.isFile() , "Configuration file %s is not a file", configurationFile);
        checkState(configurationFile.canRead(), "Configuration file %s cannot be read", configurationFile);
        
        appPreferences = new AppPreferences(configurationFile);
        webConsolePreferences = WebConsolePreferences.from(appPreferences);
        
        File chromeBin  = webConsolePreferences.getChromeBin();
        checkNotNull(chromeBin, 
                "Chrome binary path %s does not exist, please ensure you edit " + 
                "minium-prefs.json and set webconsole.chromeBin to point to chrome binary", chromeBin);
        
        checkState(chromeBin.exists()    , "Chrome binary path %s does not exist", chromeBin);
        checkState(chromeBin.isFile()    , "Chrome binary path %s is not a file", chromeBin);
        checkState(chromeBin.canExecute(), "Chrome binary path %s cannot execute", chromeBin);

        print(Strings.repeat("*", 78));
        print("* %-26s: %s", MINIUM_HOME_KEY           , appPreferences.getBaseDir().getAbsolutePath());
        print("* %-26s: %s", "webconsole.host"         , webConsolePreferences.getHost());
        print("* %-26s: %d", "webconsole.port"         , webConsolePreferences.getPort());
        print("* %-26s: %d", "webconsole.shutdownPort" , webConsolePreferences.getShutdownPort());
        print("* %-26s: %s", "webconsole.chromeBin"    , webConsolePreferences.getChromeBin().getAbsolutePath());
        print(Strings.repeat("*", 78));
    }
    
    private static void print(String msg, Object ... args) {
        System.out.println(format(msg, args));
    }
}
