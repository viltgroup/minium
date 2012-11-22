package com.vilt.minium;

import static com.vilt.minium.Minium.$;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.Sleeper;

public class AlertTest extends MiniumBaseTest {

	@Before
	public void openPage() {
		get("minium/tests/alert-test.html");
	}

	
	@Test
	public void testAlert() {
		$(wd).alert().accept();
	}
	
	@Test
	public void testAlertTimeout() {
		wd.configuration().defaultTimeout(3, TimeUnit.SECONDS);
		try {
			$(wd).alert().accept();
		} catch (TimeoutException e) {
			assertTrue(e.getCause() instanceof NoAlertPresentException);
		}
	}
	
	@Test
	public void testJQueryWhileAlert() throws InterruptedException {
		Sleeper.SYSTEM_SLEEPER.sleep(new Duration(3, SECONDS));
		try {
			$(wd, "h2").val();
		} catch (MiniumException e) {
			assertTrue(e.getCause() instanceof UnhandledAlertException);
		}
	}
	
	@Test
	public void testJQueryWhileAlerta() throws InterruptedException {
		Sleeper.SYSTEM_SLEEPER.sleep(new Duration(3, SECONDS));
		try {
			$(wd, "h2").val();
		} catch (MiniumException e) {
			wd.switchTo().alert().accept();
		}
	}
	
}
