package com.vilt.minium.webconsole.webdrivers;


public class WebDriverInfo {

    public enum Type {
        Chrome, Firefox, Safari, InternetExplorer, Opera, PhantomJS
    }

    private String variableName;
    private Type type;
    
//    private List<WindowInfo> windows;
    
    public WebDriverInfo(String variableName, Type type) {
        super();
        this.variableName = variableName;
        this.type = type;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
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
