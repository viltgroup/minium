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
package minium.web;

import minium.FreezableElements;
import minium.IterableElements;
import minium.actions.HasConfiguration;
import minium.actions.Interactable;
import minium.actions.KeyboardInteractable;
import minium.actions.MouseInteractable;
import minium.actions.WaitInteractable;
import minium.web.actions.HasAlert;
import minium.web.actions.HasBrowser;
import minium.web.actions.WebInteractable;

public interface CoreWebElements<T extends WebElements & Interactable<T>> extends
        BasicWebElements<T>,
        FreezableElements<T>,
        IterableElements<T>,
        ConditionalWebElements<T>,
        ExtensionsWebElements<T>,
        EvalWebElements<T>,
        TargetLocatorWebElements<T>,
        PositionWebElements<T>,
        HasBrowser<T>,
        HasAlert,
        HasConfiguration,
        WaitInteractable<T>,
        MouseInteractable<T>,
        KeyboardInteractable<T>,
        WebInteractable<T> {

    public interface DefaultWebElements extends CoreWebElements<DefaultWebElements> { }

}
