package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.untilEmpty;
import static com.vilt.minium.Minium.untilNotEmpty;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Sleeper;

import com.vilt.minium.Action;

public class DefaultAction implements Action<DefaultAction> {
	
	private long time = -1;
	private TimeUnit units = SECONDS;
	private String tip;
	private long tipTime = 2;
	private TimeUnit tipTimeUnit = SECONDS;
	
	public DefaultAction() {
	}
	
	public DefaultAction(long time, TimeUnit units) {
		this.time = time;
		this.units = units;		
	}
	
	public DefaultAction withTip(String tip) {
		this.tip = tip;
		return this;
	}
	
	public DefaultAction withTip(String tip, long time, TimeUnit units) {
		this.tip = tip;
		this.tipTime = time;
		this.tipTimeUnit = units;
		return this;
	}
	
	public void click(DefaultWebElements elems) {
		DefaultWebElements first = getFirst(elems);
		
		showTipIfNecessary(first);
		waitIfNecessary();
		
		first.get(0).click();
	}

	public void rightClick(DefaultWebElements elems) {
		DefaultWebElements first = getFirst(elems);
		
		showTipIfNecessary(first);
		waitIfNecessary();
		
		WebElement elem = first.get(0);
		
		new Actions(((WrapsDriver) elem).getWrappedDriver()).contextClick(elem).perform();
	}
	
	public void clickAll(DefaultWebElements elems) {
		DefaultWebElements visibleElems = elems.visible();
		visibleElems.wait(untilNotEmpty());
		
		showTipIfNecessary(visibleElems.first());
		waitIfNecessary();

		for (WebElement visibleElem : visibleElems) {
			visibleElem.click();
		}
	}

	public void moveMouse(DefaultWebElements elems) {
		DefaultWebElements first = getFirst(elems);
		
		showTipIfNecessary(first);
		waitIfNecessary();		
		
		WebElement elem = first.get(0);

		new Actions(((WrapsDriver) elem).getWrappedDriver()).moveToElement(elem).perform(); 
	}
	
	public void sendKeys(DefaultWebElements elems, CharSequence[] keys) {
		DefaultWebElements first = getFirst(elems);
		
		showTipIfNecessary(first);
		
		WebElement elem = first.get(0);
		elem.sendKeys(keys);
	
		waitIfNecessary();
	}
	
	public void clear(DefaultWebElements elems) {
		DefaultWebElements first = getFirst(elems);
		
		showTipIfNecessary(first);
		
		WebElement elem = first.get(0);
		
		elem.clear();
		
		waitIfNecessary();

	}
	
	public void clearAndType(DefaultWebElements elems, String text) {
		DefaultWebElements first = getFirst(elems);
		
		showTipIfNecessary(first);
		
		WebElement elem = first.get(0);
		
		elem.clear();
		elem.sendKeys(text);

		waitIfNecessary();
	}

	public void type(DefaultWebElements elems, String text) {
		DefaultWebElements first = getFirst(elems);
		
		showTipIfNecessary(first);
		
		WebElement elem = first.get(0);
		elem.sendKeys(text);

		waitIfNecessary();
	}

	public void select(DefaultWebElements elems, String text) {
		DefaultWebElements first = getFirst(elems.filter("select"));
		
		showTipIfNecessary(first);
		waitIfNecessary();
		
		Select select = new Select(first.get(0));
		select.selectByVisibleText(text);
	}

	public void forElements(DefaultWebElements elems) {
		if (time != -1) {
			elems.wait(time, units, untilNotEmpty());
		}
		else {
			elems.wait(untilNotEmpty());
		}
	}

	public void whileElements(DefaultWebElements elems) {
		if (time != -1) {
			elems.wait(time, units, untilEmpty());
		}
		else {
			elems.wait(untilEmpty());
		}
	}

	public boolean checkNotEmpty(DefaultWebElements elems) {
		if (time != -1) {
			elems.waitOrTimeout(time, units, untilNotEmpty());
		}
		else {
			elems.waitOrTimeout(untilNotEmpty());
		}
		return elems.size() != 0;
	}

	public boolean checkEmpty(DefaultWebElements elems) {
		if (time != -1) {
			elems.waitOrTimeout(time, units, untilEmpty());
		}
		else {
			elems.waitOrTimeout(untilEmpty());
		}
		return elems.size() == 0;
	}
	
	public void waitTime(long time, TimeUnit unit) {
		Duration duration = new Duration(time, unit);
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(duration);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}
	
	protected DefaultWebElements getFirst(DefaultWebElements elems) {
		DefaultWebElements first = elems.visible().first();
		first.wait(untilNotEmpty());
		return first;
	}
	
	private void showTipIfNecessary(DefaultWebElements elems) {
		if (tip != null && elems instanceof TipsWebElements<?>) {
			((TipsWebElements<?>) elems).showTip(tip, tipTime, tipTimeUnit);
		}
	}
	
	private void waitIfNecessary() {
		if (tip != null) waitTime(tipTime, tipTimeUnit);
	}
}
