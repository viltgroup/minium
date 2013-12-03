package com.vilt.minium.app;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.collect.Lists;
import com.vilt.minium.prefs.WebConsolePreferences;

public class EmbeddedBrowser {
	
	public interface Listener {
		public void closed();
	}
	
	private static class StreamGobbler extends Thread {
	    InputStream is;

	    private StreamGobbler(InputStream is) {
	        this.is = is;
	    }

	    @Override
	    public void run() {
	        try {
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            while (br.readLine() != null);
	        }
	        catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
	    }
	}
	
	private List<Listener> listeners = Lists.newArrayList();
	private File baseDir;
    private WebConsolePreferences webConsolePreferences;
	
	public EmbeddedBrowser(File baseDir, WebConsolePreferences webConsolePreferences) {
		this(baseDir, webConsolePreferences, null);
	}
	
	public EmbeddedBrowser(File baseDir, WebConsolePreferences webConsolePreferences, Listener listener) {
		this.baseDir = baseDir;
        this.webConsolePreferences = webConsolePreferences;
        if (listener != null) {
			addListener(listener);
		}
	}
	
	public void addListener(Listener listener) {
		checkNotNull(listener);
		listeners.add(listener);
	}

	public void start() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					String browserExecPath = webConsolePreferences.getChromeBin().getAbsolutePath();
					
					String host = webConsolePreferences.getHost();
					int port = webConsolePreferences.getPort();
					
					File userDataDir = new File(baseDir, "user-data");
					
					Process p = new ProcessBuilder(
							browserExecPath,
							format("--app=http://%s:%d/minium-webconsole/", host, port),
							"--disable-background-mode",
							format("--user-data-dir=%s", userDataDir.getAbsolutePath())
							)
							.start();
					
					StreamGobbler inGobbler = new StreamGobbler(p.getInputStream());
					StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream());
					
					// start gobblers
					inGobbler.start();
					errorGobbler.start();
					
					p.waitFor();
										
					fireListeners();
				} catch (IOException e) {
					throw new RuntimeException(e);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

		}).start();
	}
	
	private void fireListeners() {
		for (Listener listener : listeners) {
			listener.closed();
		}
	}
}
