package minium.actions;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

import minium.actions.internal.SlowMotionInteractionListener;
import minium.actions.internal.TimeoutInteractionListener;
import minium.actions.internal.WaitingPresetInteractionListener;

public class InteractionListeners {

    /**
    * Timeout.
    *
    * @param time the time
    * @param units the units
    * @return the interaction listener
    */
   public static InteractionListener timeout(long time, TimeUnit units) {
       return new TimeoutInteractionListener(new Duration(time, units));
   }

   /**
    * Timeout.
    *
    * @param preset the waiting preset
    * @return the interaction listener
    */
   public static InteractionListener waitingPreset(String preset) {
       return new WaitingPresetInteractionListener(preset);
   }

   /**
    * Instant timeout.
    *
    * @return the interaction listener
    */
   public static InteractionListener instantTimeout() {
       return timeout(0, SECONDS);
   }

   public static InteractionListener slowMotion(long time, TimeUnit units) {
       return new SlowMotionInteractionListener(new Duration(time, units));
   }

}
