package com.vilt.minium.driver.impl;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Objects;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.WebElementsFactory;

public class WindowWebElementsDriver<T extends WebElements<T>> extends WebElementsDriver<T> {

	private String handle;

	public WindowWebElementsDriver(WebDriver wd, WebElementsFactory factory, String handle) {
		super(wd, factory);
		this.handle = handle;
	}

	@Override
	public void ensureSwitch() {
		try {
			wd.switchTo().window(handle);
			handle = wd.getWindowHandle();
		} catch (NoSuchWindowException e) {
			// then lookup for the window title
			for (String windowHandle : wd.getWindowHandles()) {
		    	String title = wd.switchTo().window(windowHandle).getTitle();
				if (handle.equals(title)) {
					handle = windowHandle;
		    	}
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof WindowWebElementsDriver))
			return false;

		return Objects.equal(handle, ((WindowWebElementsDriver<?>) obj).handle);
	}
}
