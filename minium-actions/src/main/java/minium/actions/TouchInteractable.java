package minium.actions;

import minium.Offsets.Offset;


public interface TouchInteractable<T extends Interactable<?>> extends Interactable<T> {

    // from org.openqa.selenium.interactions.touch.TouchActions
    public T singleTap();

    public T down(Offset offset);

    public T up(Offset offset);

    public T move(Offset offset);

    public T doubleTap();

    public T longPress();

    public T scroll(int xOffset, int yOffset);

    public T flick(int xSpeed, int ySpeed);

}