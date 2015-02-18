package minium.web.actions;

import minium.web.WebElements;


public interface HasBrowser<T extends WebElements> {

    Browser<T> browser();

}
