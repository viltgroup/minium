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

import minium.ConditionalElements;
import minium.actions.Interactable;
import minium.internal.LocatableElements;
import minium.web.BasicWebElements;
import minium.web.EvalWebElements;
import minium.web.JQueryExtWebElements;
import minium.web.PositionWebElements;
import minium.web.ScrollWebElements;
import minium.web.TargetLocatorWebElements;

import com.vilt.minium.debug.DebugWebElements;
import com.vilt.minium.tips.TipWebElements;

/**
 * The Interface DefaultWebElements.
 *
 * @author rui.figueira
 */
public interface DefaultWebElements extends
    BasicWebElements<DefaultWebElements>,
    JQueryExtWebElements<DefaultWebElements>,
    PositionWebElements<DefaultWebElements>,
    ConditionalElements<DefaultWebElements>,
    EvalWebElements<DefaultWebElements>,
    TargetLocatorWebElements<DefaultWebElements>,
    ScrollWebElements,
    LocatableElements,
    TipWebElements,
    DebugWebElements,
    Interactable { }