/*
 * Copyright (C) 2013 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vilt.minium.debug;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElements;

/**
 * The Class ElementsScreenshotInteraction.
 */
public class ElementsScreenshotInteraction extends ScreenshotInteraction {

	/**
	 * Instantiates a new elements screenshot interaction.
	 *
	 * @param elems the elems
	 * @param stream the stream
	 */
	public ElementsScreenshotInteraction(WebElements elems, OutputStream stream) {
		super(elems, stream);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		try {
			WebElement elem = getFirstElement();
			
			Point p = elem.getLocation();
			int width = elem.getSize().getWidth();
			int height = elem.getSize().getHeight();

			CoreWebElements<?> coreWebElements = (CoreWebElements<?>) getSource();
			byte[] screenshot = coreWebElements.webDriver().getScreenshotAs(OutputType.BYTES);

			BufferedImage img = null;

			img = ImageIO.read(new ByteArrayInputStream(screenshot));
			BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, height);

			ImageIO.write(dest, "png", stream);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
