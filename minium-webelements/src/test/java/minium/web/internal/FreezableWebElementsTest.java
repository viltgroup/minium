package minium.web.internal;

import static org.junit.Assert.assertThat;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.Browser;
import minium.web.actions.WebDriverBrowser;
import minium.web.internal.drivers.MockWebDriver;
import minium.web.internal.drivers.MockWebElement;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class FreezableWebElementsTest {

    private MockWebDriver webDriver;
    private Browser<DefaultWebElements> browser;
    private DefaultWebElements root;

    @Before
    public void setup() {
        webDriver = new MockWebDriver();
        browser = new WebDriverBrowser<>(webDriver, DefaultWebElements.class);
        root = browser.root();
    }

    @Test
    public void testFreezeFirstEmptyThenNot() {
        DefaultWebElements textFld = root.find(":text").freeze();

        // first we don't return anything
        webDriver.when(root.find(":text")).thenReturn();
        assertThat(textFld, Matchers.<DefaultWebElements>iterableWithSize(0));

        // now we return 1 and so it will freeze...
        webDriver.when(root.find(":text")).thenReturn(new MockWebElement());
        assertThat(textFld, Matchers.<DefaultWebElements>iterableWithSize(1));

        // now if we return 2 native elements, textFld will return only one
        webDriver.when(root.find(":text")).thenReturn(new MockWebElement(), new MockWebElement());
        assertThat(textFld, Matchers.<DefaultWebElements>iterableWithSize(1));
    }

    @Test
    public void testFreezeFirstNotEmptyThenEmpty() {
        DefaultWebElements textFld = root.find(":text").freeze();

        // first we return 1 element
        webDriver.when(root.find(":text")).thenReturn(new MockWebElement());
        assertThat(textFld, Matchers.<DefaultWebElements>iterableWithSize(1));

        // now we return none
        webDriver.when(root.find(":text")).thenReturn();
        assertThat(textFld, Matchers.<DefaultWebElements>iterableWithSize(1));
    }
}
