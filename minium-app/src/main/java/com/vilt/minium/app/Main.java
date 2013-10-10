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

public class Main {

    private static final String MINIUM_HOME_KEY = "minium.home";
	
	private File baseDir;
	private Configuration configuration;
	
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.createServer();
	}
	
	public Main() throws IOException {
       validateAndLoadConfiguration();
	   System.out.println(format("Using Minium base dir: %s", baseDir));
    }

    public void createServer() throws Exception, InterruptedException {
        final Server server = new Server();
		
		Connector connector = new SelectChannelConnector();
		connector.setHost(configuration.getHost());
		connector.setPort(configuration.getPort());
		server.addConnector(connector);
		
		server.setStopAtShutdown(true);
		server.setGracefulShutdown(configuration.getShutdownPort());
		
		File webappDir = new File(baseDir, "webapp");
		WebAppContext context = new WebAppContext();
        context.setContextPath("/minium-webconsole");
        context.setWar(webappDir.getAbsolutePath());

        server.setHandler(context);
        
        server.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
        	@Override
        	public void lifeCycleStarted(LifeCycle event) {
        		EmbeddedBrowser browser = new EmbeddedBrowser(configuration, new EmbeddedBrowser.Listener() {
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
        
        File file = new File(path);
        checkState(file.exists(), "Path %s does not exist", path);
        checkState(file.isDirectory(), "Path %s is not a directory", path);
        
        baseDir = file;
        
        File configurationFile = new File(path, "app.properties");
        
        checkState(configurationFile.exists() , "Configuration file %s does not exist", configurationFile);
        checkState(configurationFile.isFile() , "Configuration file %s is not a file", configurationFile);
        checkState(configurationFile.canRead(), "Configuration file %s cannot be read", configurationFile);
        
        configuration = new Configuration(baseDir, configurationFile);
        
        File chromeBin  = configuration.getChromeBin();
        
        checkState(chromeBin.exists()    , "Chrome binary path %s does not exist", chromeBin);
        checkState(chromeBin.isFile()    , "Chrome binary path %s is not a file", chromeBin);
        checkState(chromeBin.canExecute(), "Chrome binary path %s cannot execute", chromeBin);

        print(Strings.repeat("*", 80));
        print("* %-26s: %s", MINIUM_HOME_KEY, configuration.getBaseDir().getAbsolutePath());
        print("* %-26s: %s", Configuration.HOST_KEY, configuration.getHost());
        print("* %-26s: %d", Configuration.PORT_KEY, configuration.getPort());
        print("* %-26s: %d", Configuration.SHUTDOWN_PORT_KEY, configuration.getShutdownPort());
        print("* %-26s: %s", Configuration.CHROME_BIN, configuration.getChromeBin().getAbsolutePath());
        print(Strings.repeat("*", 80));
    }
    
    private static void print(String msg, Object ... args) {
        System.out.println(format(msg, args));
    }
}
