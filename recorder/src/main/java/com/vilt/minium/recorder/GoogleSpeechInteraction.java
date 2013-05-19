package com.vilt.minium.recorder;

import java.util.Locale;

import com.vilt.minium.actions.AsyncInteraction;
import com.vilt.minium.actions.DefaultInteraction;

public abstract class GoogleSpeechInteraction extends DefaultInteraction implements AsyncInteraction {
	Locale locale;
	String text;
	
	public GoogleSpeechInteraction(Locale locale, String text) {
		super(null);
		this.locale = locale == null ? Locale.ENGLISH : locale;
		this.text = text;
	}

}
