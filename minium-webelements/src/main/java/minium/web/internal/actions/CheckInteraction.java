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
import minium.web.BasicWebElements;

public class CheckInteraction extends ClickInteraction {

    public CheckInteraction(Elements elems) {
        super(elems, null);
    }

    @Override
    protected void doPerform() {
        if (!getSource().as(BasicWebElements.class).is(":checked")) {
            super.doPerform();
        }
    }
}
