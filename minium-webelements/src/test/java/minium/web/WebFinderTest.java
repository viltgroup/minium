package minium.web;

import static minium.web.CoreWebElements.DefaultWebElements.by;
import minium.Minium;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.WebElementsFactory.Builder;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebFinderTest {

    private static WebDriver wd;

    @BeforeClass
    public static void setup() {
        wd = new ChromeDriver();
        Builder<DefaultWebElements> builder = new WebElementsFactory.Builder<>();
        WebModules.defaultModule(wd).configure(builder);
        DefaultWebElements root = builder.build().createRoot();
        Minium.set(root);
    }

    @AfterClass
    public static void tearDown() {
        Minium.release();
        wd.quit();
    }

    @Test
    public void test_by_name() throws Exception {
        wd.get("http://www.google.com");

        DefaultWebElements searchFld = by.name("q");

        searchFld.fill("Minium Can!");
    }

    @Test
    public void google_spreadsheet() {
        wd.get("https://docs.google.com/spreadsheet/ccc?key=0Al0ulrJIDCUVdHJjWnJsbG5hY3hBWFp0Vy1OQV9qQUE#gid=0");

        DefaultWebElements colC   = by.cssSelector("#0-grid-table-quadrantcolumn-head-section th").withText("C");
        DefaultWebElements row5   = by.cssSelector(".row-header-wrapper").withText("5");
        DefaultWebElements cellC5 = by.cssSelector("#0-grid-table-quadrantscrollable td").below(colC).rightOf(row5);

        cellC5.doubleClick();

        DefaultWebElements cellInput = by.cssSelector(".cell-input").overlaps(cellC5);

        cellInput.fill("Minium can!");
    }
}
