package com.vilt.minium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.Sleeper;

import com.vilt.minium.jquery.DefaultAction;
import com.vilt.minium.jquery.DefaultWebElements;

public class MiniumActions {

	public static void click(DefaultWebElements elems) {
		new DefaultAction().click(elems);
	}
	

	public static void rightClick(DefaultWebElements elems) {
		new DefaultAction().rightClick(elems);
	}
	
	public static void clickAll(DefaultWebElements elems) {
		new DefaultAction().clickAll(elems);
	}
	
	
	public static void moveTo(DefaultWebElements elems) {
		new DefaultAction().moveMouse(elems);
	}
	
	public static void moveTo(DefaultWebElements from, DefaultWebElements to) {
//		new Action().moveMouse(from, to);
	}
	
	public static void sendKeys(DefaultWebElements elems, CharSequence ... keys) {
		new DefaultAction().sendKeys(elems, keys);
	}
	
	public static void clear(DefaultWebElements elems) {
		new DefaultAction().clear(elems);
	}
	
	public static void clearAndType(DefaultWebElements elems, String text) {
		new DefaultAction().clearAndType(elems, text);
	}
	
	public static void type(DefaultWebElements elems, String text) {
		new DefaultAction().type(elems, text);
	}
	
	public static void select(DefaultWebElements elems, String text) {
		new DefaultAction().select(elems, text);
	}
	
	public static void waitForElements(DefaultWebElements elems) {
		new DefaultAction().forElements(elems);
	}
	
	public static void waitWhileElements(DefaultWebElements elems) {
		new DefaultAction().whileElements(elems);
	}

	public static boolean checkNotEmpty(DefaultWebElements elems) {
		return new DefaultAction().checkNotEmpty(elems);
	}

	public static boolean checkEmpty(DefaultWebElements elems) {
		return new DefaultAction().checkEmpty(elems);
	}
	
	public static void waitTime(long time, TimeUnit unit) {
		Duration duration = new Duration(time, unit);
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(duration);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}
	
	public static <A extends Action<A>> A withoutWaiting() {
		return withWaitTimeout(0, TimeUnit.SECONDS);
	}

	public static <A extends Action<A>> A withWaitTimeout(long time, TimeUnit units) {
		return null;
//		return new DefaultAction(time, units);
	}
}
