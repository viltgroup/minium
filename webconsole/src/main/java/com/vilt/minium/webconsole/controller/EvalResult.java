package com.vilt.minium.webconsole.controller;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.collect.Maps;
import com.vilt.minium.WebElements;

public class EvalResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private Object value;
	private Map<String, Object> exception;
	
	@JsonIgnore
	private Throwable realException;

	private int size = -1;	

	public EvalResult(Object value) {
		this(value, null);
	}
	
	public EvalResult(Object value, int size) {
		this(value);
		this.size = size;
	}
	
	public EvalResult(Throwable e) {
		this(null, e);
	}
	
	public EvalResult(Object value, Throwable exception) {
		if (exception != null) {
			this.realException = exception;
			this.exception = Maps.newHashMap();
			this.exception.put("class", exception.getClass().getName());
			this.exception.put("message", exception.getMessage());
			this.exception.put("fullStackTrace", ExceptionUtils.getFullStackTrace(exception));
		}
		if (value instanceof WebElements) {
			this.value = ((WebElements) value).toString();
		}
		else {
			this.value = value == null ? null : value.toString();
		}
	}
	
	public Object getValue() {
		return value;
	}
	
	public int getSize() {
		return size;
	}
	
	@JsonIgnore
	public Throwable getException() {
		return realException;
	}
	
	public Map<String, Object> getExceptionInfo() {
		return exception;
	}
}
