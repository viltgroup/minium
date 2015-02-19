package minium.web.internal.actions;

import minium.Elements;
import minium.web.internal.HasNativeWebDriver;

import org.openqa.selenium.Alert;

public abstract class AlertInteraction extends AbstractWebInteraction {

    public AlertInteraction(Elements elems) {
        super(elems);
    }

    @Override
    public void waitToPerform() {
        waitFor(WebWaitPredicates.forAlert());
    }

    protected Alert alert() {
        return getSource().as(HasNativeWebDriver.class).nativeWebDriver().switchTo().alert();
    }
}
