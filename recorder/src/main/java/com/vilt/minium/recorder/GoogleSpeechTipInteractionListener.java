package com.vilt.minium.recorder;

import static com.vilt.minium.actions.Interactions.perform;

import java.util.Locale;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.actions.AfterInteractionEvent;
import com.vilt.minium.actions.BeforeInteractionEvent;
import com.vilt.minium.actions.DefaultInteractionListener;
import com.vilt.minium.tips.TipInteraction;

public class GoogleSpeechTipInteractionListener extends DefaultInteractionListener {
	
	private Locale locale;
	private GoogleSpeechInteraction speechInteraction;
	private WebElementsDriver<CoreWebElements<?>> wd;

	public GoogleSpeechTipInteractionListener(WebElementsDriver<CoreWebElements<?>> wd) {
		this(wd, null);
	}
	
	public GoogleSpeechTipInteractionListener(WebElementsDriver<CoreWebElements<?>> wd, Locale locale) {
		this.wd = wd;
		this.locale = locale;
	}
	
	@Override
	protected void onBeforeEvent(BeforeInteractionEvent event) {
		if (event.getInteraction() instanceof TipInteraction) {
			TipInteraction tipInteraction = (TipInteraction) event.getInteraction();
			speechInteraction = wd == null ?
				new ServerSideGoogleSpeechInteraction(locale, tipInteraction.getTip()) :
				new ClientSideGoogleSpeechInteraction(wd, locale, tipInteraction.getTip());
			perform(speechInteraction);
		}
	}
	
	@Override
	protected void onAfterEvent(AfterInteractionEvent event) {
		if (event.getInteraction() instanceof TipInteraction) {
			speechInteraction.waitUntilCompleted();
		}
	}
}
