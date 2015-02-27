package minium.web;

import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.WebDriverBrowser;

import org.junit.AfterClass;
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
    public void test_by_name() throws Exception {
        browser.get("http://www.google.com");

        DefaultWebElements searchFld = browser.root().find("q");

        searchFld.fill("Minium Can!");
    }

    @Test
    public void google_spreadsheet() {
        browser.get("https://docs.google.com/spreadsheet/ccc?key=0Al0ulrJIDCUVdHJjWnJsbG5hY3hBWFp0Vy1OQV9qQUE#gid=0");

        DefaultWebElements colC   = browser.root().find("#0-grid-table-quadrantcolumn-head-section th").withText("C");
        DefaultWebElements row5   = browser.root().find(".row-header-wrapper").withText("5");
        DefaultWebElements cellC5 = browser.root().find("#0-grid-table-quadrantscrollable td").below(colC).rightOf(row5);

        cellC5.doubleClick();

        DefaultWebElements cellInput = browser.root().find(".cell-input").overlaps(cellC5);

        cellInput.fill("Minium can!");
    }
}
