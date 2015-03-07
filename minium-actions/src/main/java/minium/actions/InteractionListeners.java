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
package minium.actions;

import java.util.concurrent.TimeUnit;

import minium.actions.internal.SlowMotionInteractionListener;
import minium.actions.internal.WaitingPresetInteractionListener;

public class InteractionListeners {

   /**
    * Timeout.
    *
    * @param preset the waiting preset
    * @return the interaction listener
    */
   public static InteractionListener waitingPreset(String preset) {
       return new WaitingPresetInteractionListener(preset);
   }

   public static InteractionListener slowMotion(long time, TimeUnit units) {
       return new SlowMotionInteractionListener(new Duration(time, units));
   }

}
