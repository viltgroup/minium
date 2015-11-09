package minium.web.internal;

import static org.junit.Assert.assertThat;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.Browser;
import minium.web.actions.WebDriverBrowser;
import minium.web.internal.drivers.MockWebDriver;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class EmptyWebElementsTest {
    
    private MockWebDriver webDriver;
    private Browser<DefaultWebElements> browser;

    @Before
    public void setup() {
        webDriver = new MockWebDriver();
        browser = new WebDriverBrowser<>(webDriver, DefaultWebElements.class);
    }
    
    @Test
    public void testAdd() {
        DefaultWebElements emptyAddSomething = browser.$().add(":text");
        assertThat(emptyAddSomething.size(), Matchers.equalTo(1));
    }
}
