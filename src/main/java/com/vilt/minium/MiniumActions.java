package com.vilt.minium;

import java.util.concurrent.TimeUnit;

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

	public static void moveAndClick(DefaultWebElements elems) {
		new DefaultAction().moveAndClick(elems);
	}
	
	public static void moveTo(DefaultWebElements from, DefaultWebElements to) {
//		new DefaultAction().moveMouse(from, to);
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
	
	public static void waitUntilClosed(DefaultWebElements elems) {
		new DefaultAction().waitUntilClosed(elems);
	}
	
	public static void waitTime(long time, TimeUnit unit) {
		new DefaultAction().waitTime(time, unit);
	}
	
	public static DefaultAction withoutWaiting() {
		return withWaitTimeout(0, TimeUnit.SECONDS);
	}

	public static DefaultAction withWaitTimeout(long time, TimeUnit units) {
		return new DefaultAction(time, units);
	}
	
	public static DefaultAction withTip(String text) {
		return new DefaultAction().withTip(text);
	}
	
	public static DefaultAction withTip(String text, long time, TimeUnit unit) {
		return new DefaultAction().withTip(text, time, unit);
	}
}
