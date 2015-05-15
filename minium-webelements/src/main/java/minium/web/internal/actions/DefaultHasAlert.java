/*
 * Copyright (C) 2015 The Minium Authors
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
import minium.web.actions.Alert;
import minium.web.actions.HasAlert;
import minium.web.internal.HasNativeWebDriver;
import platypus.Mixin;

public class DefaultHasAlert extends Mixin.Impl implements HasAlert {

    class DefaultAlert implements Alert {

        @Override
        public void dismiss() {
            new AlertInteraction(DefaultHasAlert.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    alert().dismiss();
                }
            }.perform();
        }

        @Override
        public void accept() {
            new AlertInteraction(DefaultHasAlert.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    alert().accept();
                }
            }.perform();
        }

        @Override
        public String getText() {
            return nativeAlert().getText();
        }

        @Override
        public void sendKeys(final String keys) {
            new AlertInteraction(DefaultHasAlert.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    alert().sendKeys(keys);
                }
            }.perform();
        }
    }

    @Override
    public Alert alert() {
        return new DefaultAlert();
    }

    protected org.openqa.selenium.Alert nativeAlert() {
        return this.as(HasNativeWebDriver.class).nativeWebDriver().switchTo().alert();
    }
}
