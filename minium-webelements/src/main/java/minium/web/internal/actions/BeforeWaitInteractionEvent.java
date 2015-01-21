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
import minium.actions.Interaction;
import minium.actions.InteractionEvent;

public class BeforeWaitInteractionEvent extends InteractionEvent {

    private static final long serialVersionUID = 7244100369572656863L;

    public BeforeWaitInteractionEvent(Elements source, Interaction interaction) {
        super(source, interaction);
    }

    @Override
    public Type getType() {
        return Type.BEFORE_WAIT;
    }

}
