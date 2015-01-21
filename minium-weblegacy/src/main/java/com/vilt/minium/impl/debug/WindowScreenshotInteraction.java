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
package com.vilt.minium.impl.debug;

import java.io.IOException;
import java.io.OutputStream;

import minium.web.internal.HasNativeWebDriver;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.common.io.ByteSource;
import com.google.common.io.Closeables;
import com.vilt.minium.DefaultWebElements;

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
    public WindowScreenshotInteraction(DefaultWebElements elems, OutputStream stream) {
        super(elems, stream);
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
     */
    @Override
    protected void doPerform() {
        try {
            DefaultWebElements coreWebElements = getSource().as(DefaultWebElements.class);
            WebDriver webDriver = coreWebElements.as(HasNativeWebDriver.class).nativeWebDriver();
            byte[] screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
            ByteSource.wrap(screenshot).copyTo(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                Closeables.close(stream, true);
            } catch (IOException e) { }
        }
    }

}
