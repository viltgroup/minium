package com.vilt.minium.jquery;

import static com.vilt.minium.Minium.untilNotEmpty;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.Select;

import com.vilt.minium.Action;

public class DefaultAction implements Action<DefaultAction> {

	public void click(DefaultWebElements elems) {
		getFirst(elems).click();
	}

	public void moveMouse(DefaultWebElements elems) {
		WebElement first = getFirst(elems);
		new Actions(((WrapsDriver) first).getWrappedDriver()).moveToElement(first).perform(); 
	}
	
	public void sendKeys(DefaultWebElements elems, CharSequence[] keys) {
		getFirst(elems).sendKeys(keys);
	}
	
	public void clear(DefaultWebElements elems) {
		getFirst(elems).clear();
	}
	
	public void clearAndType(DefaultWebElements elems, String text) {
		WebElement first = getFirst(elems);
		first.clear();
		first.sendKeys(text);
	}
	
	protected WebElement getFirst(DefaultWebElements elems) {
		DefaultWebElements first = elems.visible().first();
		first.wait(untilNotEmpty());
		return first.get(0);
	}

	public void type(DefaultWebElements elems, String text) {
		getFirst(elems).sendKeys(text);
	}

	public void select(DefaultWebElements elems, String text) {
		WebElement first = getFirst(elems.filter("select"));
		Select select = new Select(first);
		select.selectByVisibleText(text);
	}

	public Object forElements(DefaultWebElements elems) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object whileElements(DefaultWebElements elems) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean checkNotEmpty(DefaultWebElements elems) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkEmpty(DefaultWebElements elems) {
		// TODO Auto-generated method stub
		return false;
	}
}
