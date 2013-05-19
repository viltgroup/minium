package com.vilt.minium.webconsole.factories;

import java.io.IOException;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.DisposableBean;

import com.vilt.minium.DefaultWebElementsDriver;

public interface WebDriverFactory extends DisposableBean {

	public DefaultWebElementsDriver chromeDriver();
	
	public DefaultWebElementsDriver firefoxDriver();
	
	public DefaultWebElementsDriver internetExplorerDriver(); 
	
	public DefaultWebElementsDriver webDriverFor(DesiredCapabilities capabilities) throws IOException;
	
}