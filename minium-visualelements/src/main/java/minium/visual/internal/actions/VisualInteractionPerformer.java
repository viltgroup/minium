package minium.visual.internal.actions;

import java.util.concurrent.TimeUnit;

import minium.Elements;
import minium.ElementsException;
import minium.Offsets.Offset;
import minium.actions.Keys;
import minium.actions.internal.AbstractInteractionPerformer;

public class VisualInteractionPerformer extends AbstractInteractionPerformer {

    @Override
    public void get(Elements elements, String url) {
        // TODO Auto-generated method stub

    }

    @Override
    public void submit(Elements elements) {
        // TODO Auto-generated method stub

    }

    @Override
    public void select(Elements elems, String text) {
        // TODO Auto-generated method stub
    }

    @Override
    public void selectVal(Elements elems, String val) {
        // TODO Auto-generated method stub

    }

    @Override
    public void selectAll(Elements elems) {
        // TODO Auto-generated method stub

    }

    @Override
    public void waitForExistence(Elements elems) throws ElementsException {
        // TODO Auto-generated method stub

    }

    @Override
    public void waitForExistence(Elements elems, String waitingPreset) {
        // TODO Auto-generated method stub

    }

    @Override
    public void waitForUnexistence(Elements elems) throws ElementsException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean checkForUnexistence(Elements elems, String waitingPreset) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkForExistence(Elements elems) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkForExistence(Elements elems, String waitingPreset) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkForUnexistence(Elements elems) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void waitForUnexistence(Elements elems, String waitingPreset) {
        // TODO Auto-generated method stub

    }

    @Override
    public void waitUntilClosed(Elements elems) throws ElementsException {
        // TODO Auto-generated method stub

    }

    @Override
    public void waitTime(long time, TimeUnit unit) {
        // TODO Auto-generated method stub

    }

    @Override
    public void close(Elements elements) {
        // TODO Auto-generated method stub

    }

    @Override
    public void scrollIntoView(Elements elements) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickAndHold(Elements elements) {
        perform(new ClickAndHoldInteraction(elements));
    }

    @Override
    public void clickAndHold(Elements elements, Offset offset) {
        perform(new ClickAndHoldInteraction(elements, offset));
    }

    @Override
    public void release(Elements elements) {
        perform(new ReleaseInteraction(elements));
    }

    @Override
    public void release(Elements elements, Offset offset) {
        perform(new ReleaseInteraction(elements, offset));
    }

    @Override
    public void click(Elements elements) {
        perform(new ClickInteraction(elements));
    }

    @Override
    public void click(Elements elements, Offset offset) {
        // TODO Auto-generated method stub

    }

    @Override
    public void doubleClick(Elements elements) {
        // TODO Auto-generated method stub

    }

    @Override
    public void doubleClick(Elements elements, Offset offset) {
        // TODO Auto-generated method stub

    }

    @Override
    public void moveTo(Elements elements) {
       perform(new MoveToInteraction(elements));
    }

    @Override
    public void moveTo(Elements elements, Offset offset) {
        perform(new MoveToInteraction(elements, offset));
    }

    @Override
    public void contextClick(Elements elements) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextClick(Elements elements, Offset offset) {
        // TODO Auto-generated method stub

    }

    @Override
    public void dragAndDrop(Elements source, Elements target) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickAll(Elements elements) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clickAll(Elements elements, Offset offset) {
        // TODO Auto-generated method stub

    }

    @Override
    public void check(Elements elements) {
        // TODO Auto-generated method stub

    }

    @Override
    public void uncheck(Elements elements) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deselect(Elements elems, String text) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deselectVal(Elements elems, String val) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deselectAll(Elements elems) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clear(Elements elements) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyDown(Elements elements, Keys keys) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyUp(Elements elements, Keys keys) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendKeys(Elements elements, CharSequence... keys) {
        // TODO Auto-generated method stub

    }

    @Override
    public void type(Elements elements, CharSequence text) {
        perform(new TypeInteraction(elements, text));
    }

    @Override
    public void fill(Elements elements, CharSequence text) {
        perform(new FillInteraction(elements, text));
    }

}
