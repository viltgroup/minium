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
package com.vilt.minium;

import org.openqa.selenium.Alert;

/**
 * The Interface TargetLocatorWebElements.
 *
 * @param <T> the generic type
 * @author Rui
 */
public interface TargetLocatorWebElements<T extends CoreWebElements<T>> extends WebElements {
	
	/**
	 * Frame.
	 *
	 * @return the t
	 */
	public T frame();

	/**
	 * Frame.
	 *
	 * @return the t
	 */
	public T frame(boolean freeze);
	
	/**
	 * Window.
	 *
	 * @return the t
	 */
	public T window();
	
	/**
	 * Window.
	 *
	 * @param freeze the freeze
	 * @return the t
	 */
	public T window(boolean freeze);

	/**
	 * Root.
	 *
	 * @return the t
	 */
	public T root();
	
	/**
	 * Root.
	 *
	 * @param freeze the freeze
	 * @return the t
	 */
	public T root(boolean freeze);
	
	/**
	 * Alert.
	 *
	 * @return the alert
	 */
	public Alert alert();
	
	public void close();

}
