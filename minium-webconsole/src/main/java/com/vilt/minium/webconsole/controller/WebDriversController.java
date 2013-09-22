package com.vilt.minium.webconsole.controller;

import static com.vilt.minium.Minium.$;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.actions.DebugInteractions;
import com.vilt.minium.script.WebElementsDrivers;
import com.vilt.minium.webconsole.webdrivers.WebDriverInfo;
import com.vilt.minium.webconsole.webdrivers.WebElementsDriverManager;
import com.vilt.minium.webconsole.webdrivers.WebDriverInfo.Type;

@Controller
@RequestMapping("/webDrivers")
public class WebDriversController {
    
    @Autowired private WebElementsDriverManager wdManager;
    @Autowired private WebElementsDrivers factory;
    
    @RequestMapping(value = "/{var}/create")
    @ResponseBody
    @ResponseStatus(CREATED)
    public void create(@PathVariable String var, @RequestParam("type") Type type) {
        if (wdManager.contains(var)) throw new IllegalStateException(format("Variable %s already exists", var));
        
        WebElementsDriver<?> wd;
        switch (type) {
        case Chrome:
            wd = factory.chromeDriver();
            break;
        case Firefox:
            wd = factory.firefoxDriver();
            break;
        case InternetExplorer:
            wd = factory.internetExplorerDriver();
            break;
        case Opera:
            throw new UnsupportedOperationException("Not implemented yet");
        case Safari:
            throw new UnsupportedOperationException("Not implemented yet");
        case PhantomJS:
            wd = factory.ghostDriver();
            break;
        default:
            throw new IllegalArgumentException("Invalid type");
        }
        
        wdManager.put(var, type, wd);
    }
    
    @RequestMapping(value = "")
    @ResponseBody
    public List<WebDriverInfo> getWebDriverVariables() {
        return wdManager.getWebDriverVariables();
    }
    
    @RequestMapping(value = "/{var}/quit")
    public ResponseEntity<Void> quit(@PathVariable("var") String var) {
        if (wdManager.delete(var)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/{var}/screenshot")
    public ResponseEntity<byte[]> takeWindowScreenshot(@PathVariable("var") String var) {
        WebElementsDriver<?> wd = wdManager.get(var);
        if (wd == null) return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DebugInteractions.takeWindowScreenshot($(wd), out);
        
        // disable cache
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");
        headers.setPragma("no-cache");
        headers.setExpires(0);
        headers.setContentType(MediaType.IMAGE_PNG);
        
        return new ResponseEntity<byte[]>(out.toByteArray(), headers, HttpStatus.OK);
    }
}
