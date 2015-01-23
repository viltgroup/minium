package minium.visual.internal.actions;

import java.util.concurrent.TimeUnit;

import minium.Elements;
import minium.Offsets.Offset;
import minium.actions.Keys;
import minium.actions.TimeoutException;
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
    public void waitWhileEmpty(Elements elems) throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public void waitWhileNotEmpty(Elements elems) throws TimeoutException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean checkNotEmpty(Elements elems) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean checkEmpty(Elements elems) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void waitUntilClosed(Elements elems) throws TimeoutException {
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
        // TODO Auto-generated method stub

    }

    @Override
    public void clickAndHold(Elements elements, Offset offset) {
        // TODO Auto-generated method stub

    }

    @Override
    public void release(Elements element) {
        // TODO Auto-generated method stub

    }

    @Override
    public void release(Elements element, Offset offset) {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub

    }

    @Override
    public void moveTo(Elements elements, Offset offset) {
        // TODO Auto-generated method stub

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
        // TODO Auto-generated method stub

    }

    @Override
    public void fill(Elements elements, CharSequence text) {
        perform(new FillInteraction(elements, text));
    }

}
