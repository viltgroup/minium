package minium.web.internal.actions;

import minium.Elements;
import minium.web.actions.Alert;
import minium.web.actions.HasAlert;
import platypus.Mixin;

public class DefaultHasAlert extends Mixin.Impl implements HasAlert {

    class DefaultAlert implements Alert {

        @Override
        public void dismiss() {
            new AlertInteraction(DefaultHasAlert.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    alert().dismiss();
                }
            }.perform();
        }

        @Override
        public void accept() {
            new AlertInteraction(DefaultHasAlert.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    alert().accept();
                }
            }.perform();

        }

        @Override
        public String getText() {
            return alert().getText();
        }

        @Override
        public void sendKeys(final String keys) {
            new AlertInteraction(DefaultHasAlert.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    alert().sendKeys(keys);
                }
            }.perform();
        }
    }

    @Override
    public Alert alert() {
        return new DefaultAlert();
    }
}
