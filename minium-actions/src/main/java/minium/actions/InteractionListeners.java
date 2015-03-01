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
