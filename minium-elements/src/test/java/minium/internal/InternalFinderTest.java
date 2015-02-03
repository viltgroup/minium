package minium.internal;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import minium.Elements;
import minium.FindElements;
import minium.Finder;

import org.junit.Test;
import org.mockito.Mockito;

import platypus.AbstractMixinInitializer;
import platypus.MixinClass;
import platypus.MixinClasses;

public class InternalFinderTest {

    public interface FooElements<T extends Elements> extends Elements {
        public T foo();
    }

    public interface BarElements<T extends Elements> extends Elements {
        public T bar();
    }

    public interface OtherElements<T extends Elements> extends Elements {
        public T other();
    }

    public interface TestElements extends FindElements<TestElements>, FooElements<TestElements>, BarElements<TestElements> { }
    public interface OtherTestElements extends OtherElements<OtherTestElements> { }
    public interface CombinedTestElements extends TestElements, OtherTestElements { }

    @Test
    public void testFinderChain() {
        Finder<TestElements> by = Finder.by(TestElements.class, OtherElements.class);
        TestElements fooBar = by.selector(":text").foo().bar();
        assertThat(fooBar, instanceOf(InternalFinder.class));

        OtherElements<?> other = fooBar.as(OtherElements.class);
        assertThat(other, instanceOf(InternalFinder.class));

        TestElements bar = other.as(TestElements.class).bar();
        assertThat(bar, instanceOf(InternalFinder.class));
    }

    @Test
    public void testFinderEval() {
        Finder<TestElements> by = Finder.by(TestElements.class, CombinedTestElements.class);
        Elements longFinder = by.selector(":text").foo().bar().as(OtherTestElements.class).other();

        final TestElements elems = mock(TestElements.class, RETURNS_DEEP_STUBS);
        final OtherTestElements otherElems = mock(OtherTestElements.class, RETURNS_DEEP_STUBS);
        final CombinedTestElements result = mock(CombinedTestElements.class, RETURNS_DEEP_STUBS);

        MixinClass<CombinedTestElements> testMixinClass = MixinClasses.create(CombinedTestElements.class);
        CombinedTestElements mixin = testMixinClass.newInstance(new AbstractMixinInitializer() {
            @Override
            protected void initialize() {
                implement(TestElements.class).with(elems);
                implement(OtherTestElements.class).with(otherElems);
            }
        });

        when(elems.find(Mockito.same(":text")).foo().bar()).thenReturn(mixin);
        when(otherElems.other()).thenReturn(result);

        Elements evalElems = longFinder.as(InternalFinder.class).eval(elems);
        assertThat(evalElems, instanceOf(CombinedTestElements.class));
        assertThat((CombinedTestElements) evalElems, sameInstance(result));
        verify(elems).find(":text");
        verify(otherElems).other();
    }
}
;