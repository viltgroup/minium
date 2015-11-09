/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.web;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.WebDriverBrowser;

import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserIT {

    private static WebDriver wd;
    private static WebDriverBrowser<DefaultWebElements> browser;

    @BeforeClass
    public static void setup() {
        wd = new ChromeDriver();
        browser = new WebDriverBrowser<>(wd, DefaultWebElements.class);
    }

    @AfterClass
    public static void tearDown() {
        wd.quit();
    }
    
    @Test
    public void testEmptyElement() throws Exception {
        browser.get("http://minium.vilt.io/sample-app/");
        
        DefaultWebElements emptyPlusTitleElem = browser.$().add(browser.$("title"));
        assertThat(emptyPlusTitleElem.size(), equalTo(1));
        assertThat(emptyPlusTitleElem.text(), equalTo("Minium Mail"));
    }

    @Test
    public void testByName() throws Exception {
        browser.get("http://www.google.com");

        DefaultWebElements add = browser.$().add(browser.$("title"));
        DefaultWebElements searchFld = browser.root().find("q");

        searchFld.fill("Minium Can!");
    }

    @Test
    public void googleSpreadsheet() {
        browser.get("https://docs.google.com/spreadsheet/ccc?key=0Al0ulrJIDCUVdHJjWnJsbG5hY3hBWFp0Vy1OQV9qQUE#gid=0");

        DefaultWebElements colC   = browser.$("#0-grid-table-quadrantcolumn-head-section th").withText("C");
        DefaultWebElements row5   = browser.$(".row-header-wrapper").withText("5");
        DefaultWebElements cellC5 = browser.$("#0-grid-table-quadrantscrollable td").below(colC).rightOf(row5);

        cellC5.doubleClick();

        DefaultWebElements cellInput = browser.$(".cell-input").overlaps(cellC5);

        cellInput.fill("Minium can!");
    }
}
