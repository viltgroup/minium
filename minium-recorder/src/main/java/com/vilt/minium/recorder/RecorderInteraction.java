package com.vilt.minium.recorder;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.DefaultInteraction;
import com.vilt.minium.recorder.impl.WebElementsScreenRecorder;

public abstract class RecorderInteraction extends DefaultInteraction {

	protected WebElementsScreenRecorder recorder;

	public RecorderInteraction(WebElements elems, WebElementsScreenRecorder recorder) {
		super(elems);
		this.recorder = recorder;
	}
}
