package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilNotEmpty;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;

import com.google.common.collect.Lists;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.Duration;
import com.vilt.minium.WebElements;
import com.vilt.minium.actions.InteractionEvent.Type;

public class DefaultInteraction implements Interaction {

	private List<InteractionListener> listeners = Lists.newArrayList();
	private Actions actions;
	private WebElements source;
	private Duration timeout;
	
	public DefaultInteraction(WebElements elems) {
		this.source = elems;
	}
	
	public void setTimeout(Duration timeout) {
		this.timeout = timeout;
	}
	
	public Duration getTimeout() {
		return timeout;
	}
	
	public void perform() {
		trigger(Type.BEFORE);
		try {
			doPerform();
			triggerReverse(Type.AFTER);
		} catch (RuntimeException e) {
			triggerReverse(Type.AFTER_FAILING);
			throw e;
		}
	}

	protected void doPerform() {
		actions.perform();
	}

	@Override
	public void registerListener(InteractionListener listener) {
		listeners.add(listener);
	}

	@Override
	public void unregisterListener(InteractionListener listener) {
		listeners.remove(listener);
	}
		
	protected void trigger(Type type) {
		trigger(getAllListeners(), type);
	}
	
	protected void triggerReverse(Type type) {
		trigger(Lists.reverse(getAllListeners()), type);
	}
	
	protected void trigger(List<InteractionListener> listeners, Type type) {
		if (listeners.isEmpty()) return;
		InteractionEvent event = new InteractionEvent(source, this, type);
		for (InteractionListener listener : listeners) {
			listener.onEvent(event);
		}
	}

	protected WebElements getFirst(WebElements elems) {
		WebElements first = ((CoreWebElements<?>) elems).visible().first();
		first = ((CoreWebElements<?>) first).wait(timeout, untilNotEmpty());
		return first;
	}

	protected WebElement getFirstElement(WebElements elems) {
		WebElements first = getFirst(elems);
		return first.get(0);
	}

	protected Actions newActions(WebElement elem) {
		return new Actions(((WrapsDriver) elem).getWrappedDriver());
	}
	
	protected WebElement getFirstElement() {
		return getFirstElement(source);
	}

	protected Actions getActions() {
		return newActions(getFirstElement(source));
	}
	
	public WebElements getSource() {
		return source;
	}
	
	private List<InteractionListener> getAllListeners() {
		List<InteractionListener> allListeners = Lists.newArrayList();
		if (source != null) {
			List<InteractionListener> globalListeners = ((CoreWebElements<?>) source).configuration().getGlobalListeners();			
			allListeners.addAll(globalListeners);
		}
		allListeners.addAll(listeners);
		
		return allListeners;
	}
}
