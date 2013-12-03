package com.vilt.minium.webconsole.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.vilt.minium.prefs.AppPreferences;

@Controller
@RequestMapping("/appPreferences")
public class AppPreferencesController {

    @Autowired
    private AppPreferences preferences;
    
    @RequestMapping(method = GET)
    @ResponseBody
    public JsonNode getPreferences() {
        return preferences.asJson();
    }
}
