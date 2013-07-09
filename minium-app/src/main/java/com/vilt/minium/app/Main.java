package com.vilt.minium.app;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {

	private static final String MINIUM_HOME_KEY = "minium.home";

	static final String LOOPBACK = "127.0.0.1";
	// Calculated using the formula: abs("minium".hashCode()) % 49152
	static final int DEFAULT_PORT = 18129;
	static final int DEFAULT_SHUTDOWN_PORT = 18130;
	
	public static void main(String[] args) throws Exception {
		System.out.println(format("Using Minium base dir: %s", miniumBaseDir()));
		
		final Server server = new Server();
		
		Connector connector = new SelectChannelConnector();
		connector.setHost(LOOPBACK);
		connector.setPort(DEFAULT_PORT);
		server.addConnector(connector);
		
		server.setStopAtShutdown(true);
		server.setGracefulShutdown(DEFAULT_SHUTDOWN_PORT);
		
		File webappDir = new File(miniumBaseDir(), "webapp");
		WebAppContext context = new WebAppContext();
        context.setContextPath("/minium-webconsole");
        context.setWar(webappDir.getAbsolutePath());

        server.setHandler(context);
        
        server.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
        	@Override
        	public void lifeCycleStarted(LifeCycle event) {
        		EmbeddedBrowser browser = new EmbeddedBrowser(new EmbeddedBrowser.Listener() {
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
	

	protected static File miniumBaseDir() {
		String path = System.getProperty(MINIUM_HOME_KEY);
		checkNotNull(path);

		File file = new File(path);
		checkState(file.exists() && file.isDirectory());
		
		return file.getAbsoluteFile();
	}

}
