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
package minium.web.internal.actions;

import minium.Elements;
import minium.web.DocumentWebDriver;
import minium.web.internal.HasNativeWebDriver;
import minium.web.internal.InternalWebElements;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Predicate;

public class WebWaitPredicates {

    /**
     * Until window closed.
     *
     * @param <T> the generic type
     * @return the predicate
     */
    public static <T extends Elements> Predicate<T> forAlert() {
        return new Predicate<T>() {
            @Override
            public boolean apply(T elems) {
                WebDriver nativeWebDriver = elems.as(HasNativeWebDriver.class).nativeWebDriver();
                try {
                    return nativeWebDriver.switchTo().alert() != null;
                } catch (NoAlertPresentException e) {
                    return false;
                }
            }
        };
    }

    /**
     * Until window closed.
     *
     * @param <T> the generic type
     * @return the predicate
     */
    public static <T extends Elements> Predicate<T> forClosedWindow() {
        return new Predicate<T>() {
            @Override
            public boolean apply(T elems) {
                DocumentWebDriver webDriver = elems.as(InternalWebElements.class).documentDriver();
                return webDriver.isClosed();
            }
        };
    }
}
