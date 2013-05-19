package com.vilt.minium.recorder;

import static com.vilt.minium.recorder.RecorderInteractions.speak;

import java.util.Locale;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.actions.BeforeInteractionEvent;
import com.vilt.minium.actions.ClickInteraction;
import com.vilt.minium.actions.DefaultInteractionListener;

public class GuideListener extends DefaultInteractionListener {
	
	private Locale locale;
	private WebElementsDriver<CoreWebElements<?>> wd;

	public GuideListener(WebElementsDriver<CoreWebElements<?>> wd) {
		this(wd, null);
	}
	
	public GuideListener(WebElementsDriver<CoreWebElements<?>> wd, Locale locale) {
		this.wd = wd;
		this.locale = locale;
	}
	
	@Override
	protected void onBeforeEvent(BeforeInteractionEvent event) {
		if (event.getInteraction() instanceof ClickInteraction) {
			WebElements source = ((ClickInteraction) event.getInteraction()).getSource();
			speak("Click ");
		}
	}
}
