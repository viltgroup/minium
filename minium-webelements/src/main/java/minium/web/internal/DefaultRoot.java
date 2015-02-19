package minium.web.internal;

import java.util.Collections;

import minium.web.DocumentWebDriver;
import minium.web.WebElements;

public class DefaultRoot<T extends WebElements> extends BaseDocumentRoots<T> {

    private final DocumentWebDriver wd;

    public DefaultRoot(DocumentWebDriver wd) {
        this.wd = wd;
    }

    @Override
    public Iterable<DocumentWebDriver> candidateDocumentDrivers() {
        return Collections.singleton(wd);
    }

    @Override
    public String toString() {
        return "";
    }

}
