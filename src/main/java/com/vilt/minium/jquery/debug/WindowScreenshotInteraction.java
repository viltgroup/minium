package com.vilt.minium.jquery.debug;

import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.DefaultInteraction;
import com.vilt.minium.jquery.CoreWebElements;

public class WindowScreenshotInteraction extends DefaultInteraction {

	private OutputStream stream;

	public WindowScreenshotInteraction(WebElements elems, OutputStream stream) {
		super(elems);
		this.stream = stream;
	}
	
	@Override
	protected void doPerform() {
		try {
			CoreWebElements<?> coreWebElements = (CoreWebElements<?>) getSource();
			byte[] screenshot = coreWebElements.webDriver().getScreenshotAs(OutputType.BYTES);
			IOUtils.write(screenshot, stream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(stream);
		}
	}
	
}
