package minium.web;

import java.net.URL;

import minium.Elements;

import org.junit.Test;

import com.google.common.reflect.TypeToken;

public class WebFinderTest {

    public interface VisualElements<VE > extends Elements {
        public VE find(URL image);
    }

    public interface TestWebElements extends Elements, BasicWebElements<TestWebElements>, PositionWebElements<TestWebElements> {}
    public interface TestVisualElements extends VisualElements<TestVisualElements> {}

    @Test
    public void test_by_name() throws Exception {
        WebFinder<TestWebElements> by = WebFinder.by(TestWebElements.class);
        TestVisualElements visualElements = by.cssSelector("h3 a").above("h2.product").filter(":not(:visited)").as(TestVisualElements.class).find(new URL("http://www.google.com"));
        System.out.println(visualElements.getClass());
    }

    public static <TT> TypeToken<TT> createTypeToken(Class<TT> clazz) {
        return TypeToken.of(clazz);
    }
}
