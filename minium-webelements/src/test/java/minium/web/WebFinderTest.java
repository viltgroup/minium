package minium.web;

import minium.actions.Interactable;
import minium.internal.InternalFinder;
import minium.web.WebElementsFactory.Builder;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebFinderTest {

    private static final WebFinder<DefaultWebElements> by = new WebFinder<>(DefaultWebElements.class);

    private static WebDriver wd;
    private static WebElementsFactory<DefaultWebElements> factory;

    @BeforeClass
    public static void setup() {
        wd = new ChromeDriver();
        Builder<DefaultWebElements> builder = new WebElementsFactory.Builder<>();
        WebModules.defaultModule(wd).configure(builder);
        factory = builder.build();
    }

    @AfterClass
    public static void tearDown() {
        wd.quit();
    }

    @Test
    public void test_by_name() throws Exception {
        wd.get("http://www.google.com");

        DefaultWebElements searchFld = by.name("q");

        DefaultWebElements root = factory.createRoot();
        Interactable fld = eval(root, searchFld);

        fld.fill("Minium Can!");
    }

    @Test
    public void google_spreadsheet() {
        wd.get("https://docs.google.com/spreadsheet/ccc?key=0Al0ulrJIDCUVdHJjWnJsbG5hY3hBWFp0Vy1OQV9qQUE#gid=0");

        DefaultWebElements root = factory.createRoot();

        DefaultWebElements colC   = by.cssSelector("#0-grid-table-quadrantcolumn-head-section th").withText("C");
        DefaultWebElements row5   = by.cssSelector(".row-header-wrapper").withText("5");
        DefaultWebElements cellC5 = eval(root, by.cssSelector("#0-grid-table-quadrantscrollable td").below(colC).rightOf(row5));

        cellC5.doubleClick();

        DefaultWebElements cellInput = eval(root, by.cssSelector(".cell-input").overlaps(cellC5));

        cellInput.fill("Minium can!");
    }

    private DefaultWebElements eval(DefaultWebElements root, DefaultWebElements finder) {
        return finder.as(InternalFinder.class).eval(root).as(DefaultWebElements.class);
    }

}
