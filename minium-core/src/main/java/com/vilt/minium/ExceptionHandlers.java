package com.vilt.minium;

import com.vilt.minium.impl.AcceptAlertExceptionHandler;

public class ExceptionHandlers {
	public static ExceptionHandler alwaysAcceptUnhandledAlerts() {
		return new AcceptAlertExceptionHandler();
	}
}
