package com.vilt.minium.impl.actions;

import com.vilt.minium.actions.BeforeInteractionEvent;
import com.vilt.minium.actions.Interaction;

public class WaitingPresetInteractionListener  extends DefaultInteractionListener {

    private String preset;

    /**
     * Instantiates a new timeout interaction listener.
     *
     * @param timeout the timeout
     */
    public WaitingPresetInteractionListener(String preset) {
        this.preset = preset;
    }
    
    /* (non-Javadoc)
     * @see com.vilt.minium.actions.DefaultInteractionListener#onBeforeEvent(com.vilt.minium.actions.InteractionEvent)
     */
    @Override
    protected void onBeforeEvent(BeforeInteractionEvent event) {
        Interaction interaction = event.getInteraction();
        if (interaction instanceof DefaultInteraction) {
            DefaultInteraction defInteraction = (DefaultInteraction) interaction;
            defInteraction.setWaitingPreset(preset);
        }
    }
}
 