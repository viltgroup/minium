package minium.actions;

import minium.Elements;
import minium.Offsets.Offset;

public interface Interactable {
    public void close();
    public void scrollIntoView();
    public void clear();
    public void submit();
    public void keyDown(Keys keys);
    public void keyUp(Keys keys);
    public void sendKeys(CharSequence ... keys);
    public void clickAndHold();
    public void clickAndHold(Offset offset);
    public void release();
    public void release(Offset offset);
    public void click();
    public void doubleClick();
    public void moveTo();
    public void moveTo(Offset offset);
    public void contextClick();
    public void dragAndDrop(Elements target);
    public void clickAll();
    public void type(CharSequence text);
    public void fill(CharSequence text);
    public void check();
    public void uncheck();
    public void select(String text);
    public void deselect(String text);
    public void selectVal(String text);
    public void deselectVal(String text);
    public void selectAll();
    public void deselectAll();
    public void waitWhileEmpty();
    public void waitWhileNotEmpty();
    public void checkNotEmpty();
    public void checkEmpty();
    public void waitUntilClosed();
}
