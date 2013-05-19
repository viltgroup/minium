package com.vilt.minium.recorder;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.actions.Interactions.click;
import static com.vilt.minium.actions.Interactions.waitTime;
import static com.vilt.minium.recorder.RecorderInteractions.startRecordingScreen;
import static com.vilt.minium.recorder.RecorderInteractions.stopRecording;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;

import com.vilt.minium.DefaultWebElementsDriver;

public class ScreenTest {
	public static void main(String[] args) {
		DefaultWebElementsDriver wd = new DefaultWebElementsDriver(new ChromeDriver());
		
		startRecordingScreen(new File("d:/Dev/foo.avi"));
		
		try {
			wd.get("http://translate.google.com/#auto/en/hello%20world");
			click($(wd, "#gt-src-listen"));
			waitTime(3, TimeUnit.SECONDS);
			wd.quit();
		}
		finally {
			stopRecording();
		}
	}
}
