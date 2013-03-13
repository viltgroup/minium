package com.vilt.minium.actions;

import java.util.EventObject;

import com.vilt.minium.WebElements;

public class ActionEvent extends EventObject {

	private static final long serialVersionUID = -1830111797395332704L;

	public enum ActionEventType {
		BEFORE,
		AFTER
	}
	
	private ActionEventType type;
	private String operation;
	private Object[] args;

	public ActionEvent(ActionEventType type, String operation, WebElements source, Object ... args) {
		super(source);
		this.type = type;
		this.operation = operation;
		this.args = args;
	}
	
	@Override
	public WebElements getSource() {
		return (WebElements) super.getSource();
	}
	
	public ActionEventType getType() {
		return type;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public Object[] getArgs() {
		return args;
	}
}
