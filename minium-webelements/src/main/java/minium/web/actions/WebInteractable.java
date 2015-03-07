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
package minium.web.actions;

import minium.actions.Interactable;

public interface WebInteractable<T extends Interactable<?>> extends Interactable<T> {
    public T submit();
    public T check();
    public T uncheck();
    public T select(String text);
    public T deselect(String text);
    public T selectVal(String text);
    public T deselectVal(String text);
    public T selectAll();
    public T deselectAll();
    public T scrollIntoView();
    public T close();
}
