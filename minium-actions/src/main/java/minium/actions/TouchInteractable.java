package minium.actions;


public interface TouchInteractable<T extends Interactable<?>> extends Interactable<T> {

    // from org.openqa.selenium.interactions.touch.TouchActions
    public T singleTap();

    public T down(int x, int y);

    public T up(int x, int y);

    public T move(int x, int y);

    public T doubleTap();

    public T longPress();

    public T scroll(int xOffset, int yOffset);

    public T flick(int xSpeed, int ySpeed);

}