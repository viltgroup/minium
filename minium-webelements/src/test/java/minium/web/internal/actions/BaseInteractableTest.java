package minium.web.internal.actions;

import static org.mockito.Mockito.spy;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.WebDriverBrowser;
import minium.web.internal.drivers.MockWebDriver;
import minium.web.internal.drivers.MockWebElement;

import org.junit.Before;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;

public class BaseInteractableTest {

    private MockWebDriver mockedWebDriver;
    protected MockWebElement mockedWebElement;
    private WebDriverBrowser<DefaultWebElements> browser;
    protected DefaultWebElements interactable;
    protected Mouse mouse;
    protected Keyboard keyboard;

    public BaseInteractableTest() {
        super();
    }

    @Before
    public void setup() {
        mockedWebElement = spy(new MockWebElement());
        mockedWebDriver = spy(new MockWebDriver());
        browser = new WebDriverBrowser<>(mockedWebDriver, DefaultWebElements.class);
    
        interactable = browser.root();
        mouse = mockedWebDriver.getMouse();
        keyboard = mockedWebDriver.getKeyboard();
    
        mockedWebDriver.when(browser.root()).thenReturn(mockedWebElement);
    }

}