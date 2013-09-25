package com.vilt.minium.webconsole.webdrivers;

public class WebDriverInfo {

    public enum Type {
        Chrome, Firefox, Safari, InternetExplorer, Opera, PhantomJS
    }

    private String varName;
    private Type type;
    
//    private List<WindowInfo> windows;
    
    public WebDriverInfo(String varName, Type type) {
        super();
        this.varName = varName;
        this.type = type;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

//    public List<WindowInfo> getWindows() {
//        return windows;
//    }
//
//    public void setWindows(List<WindowInfo> windows) {
//        this.windows = windows;
//    }

}
