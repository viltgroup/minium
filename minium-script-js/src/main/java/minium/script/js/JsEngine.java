package minium.script.js;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.springframework.beans.factory.DisposableBean;

public interface JsEngine extends DisposableBean {

    public abstract <T> T runScript(File sourceFile) throws IOException;

    public abstract <T> T runScript(String path) throws IOException;

    public abstract <T> T runScript(Reader reader, String sourceName) throws IOException;

    public abstract <T> T eval(String expression, int line);

    public abstract boolean contains(String varName);

    public abstract Object get(String varName);

    public abstract <T> T get(String varName, Class<T> clazz);

    public abstract void put(String varName, Object object);

    public abstract void delete(String varName);

    public abstract void putJson(String varName, String json);

    public abstract StackTraceElement[] getExecutionStackTrace();

    public abstract void cancel();

    public abstract boolean isRunning();

}