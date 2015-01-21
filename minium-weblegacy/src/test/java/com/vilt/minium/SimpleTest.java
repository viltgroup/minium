package com.vilt.minium;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.actions.DebugInteractions.highlight;
import static com.vilt.minium.actions.Interactions.click;
import static com.vilt.minium.actions.Interactions.doubleClick;
import static com.vilt.minium.actions.Interactions.fill;
import static com.vilt.minium.actions.Interactions.get;
import static com.vilt.minium.actions.Interactions.sendKeys;
import static com.vilt.minium.speech.SpeechInteractions.speak;
import minium.actions.Keys;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class SimpleTest {

    private static DefaultWebElementsDriver wd;

    @BeforeClass
    public static void setup() {
        wd = new DefaultWebElementsDriver(new ChromeDriver());
    }

    @AfterClass
    public static void tearDown() {
        wd.quit();
    }

    @Test
    public void search_google() {
        get(wd, "http://www.google.com/ncr");

        speak("Hello, I'm Minium, and I'm alive");
        speak("Let me highlight google search box");

        DefaultWebElements searchbox = $(wd, ":text").withName("q");
        highlight(searchbox);

        speak("Minium = Minion + Selenium. Let's find out what is a Minion.");

        fill(searchbox, "minion");
        sendKeys(searchbox, Keys.ENTER);

        DefaultWebElements wikipediaResult = $(wd, "h3 a").withText("Minion - Wikipedia, the free encyclopedia");
        click(wikipediaResult);

        DefaultWebElements firstParagraph = $(wd, "#mw-content-text p").first();
        highlight(firstParagraph);

        speak("Wikipedia says: " + firstParagraph.text());
    }

    @Test
    public void google_spreadsheet() {
        get(wd, "https://docs.google.com/spreadsheet/ccc?key=0Al0ulrJIDCUVdHJjWnJsbG5hY3hBWFp0Vy1OQV9qQUE#gid=0");

        DefaultWebElements colC = $(wd, "#0-grid-table-quadrantcolumn-head-section th").withText("C");
        DefaultWebElements row5 = $(wd, ".row-header-wrapper").withText("5");
        DefaultWebElements cellC5 = $(wd, "#0-grid-table-quadrantscrollable td").below(colC).rightOf(row5);

        doubleClick(cellC5);

        DefaultWebElements cellInput = $(wd, ".cell-input").overlaps(cellC5);

        fill(cellInput, "Minium can!");
    }


}
