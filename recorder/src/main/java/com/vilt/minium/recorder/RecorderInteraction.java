package com.vilt.minium.recorder;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.DefaultInteraction;
import com.vilt.minium.recorder.impl.MiniumScreenRecorder;

public abstract class RecorderInteraction extends DefaultInteraction {

	protected MiniumScreenRecorder recorder;

	public RecorderInteraction(WebElements elems, MiniumScreenRecorder recorder) {
		super(elems);
		this.recorder = recorder;
	}
}
