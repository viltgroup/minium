package com.vilt.minium.debug;

import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.OutputType;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElements;

/**
 * The Class WindowScreenshotInteraction.
 */
public class WindowScreenshotInteraction extends ScreenshotInteraction {

	/**
	 * Instantiates a new window screenshot interaction.
	 *
	 * @param elems the elems
	 * @param stream the stream
	 */
	public WindowScreenshotInteraction(WebElements elems, OutputStream stream) {
		super(elems, stream);
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
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
