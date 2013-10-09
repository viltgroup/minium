package com.vilt.minium.impl.actions;

import static com.vilt.minium.actions.Interactions.waitTime;

import com.vilt.minium.Duration;
import com.vilt.minium.actions.BeforeInteractionEvent;
import com.vilt.minium.actions.Interaction;

public class SlowMotionInteractionListener extends DefaultInteractionListener {
    
    private Duration duration;

    public SlowMotionInteractionListener(Duration duration) {
        this.duration = duration;
    }
    
    @Override
    protected void onBeforeEvent(BeforeInteractionEvent event) {
        Interaction interaction = event.getInteraction();
        // we skip WaitInteractions
        if (interaction instanceof WaitInteraction) return;
        
        waitTime(duration.getTime(), duration.getUnit());
    }
}
