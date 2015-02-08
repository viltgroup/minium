package minium.web.actions;

public interface Alert {

    void dismiss();

    void accept();

    String getText();

    void sendKeys(String keys);
}
