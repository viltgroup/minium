package minium.actions.internal;

import minium.BasicElements;
import minium.Elements;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class WaitPredicates {

    /**
     * Predicate to use with {@link WaitWebElements#wait(Predicate)} methods which ensures that
     * evaluation will only be successful when this instance has a specific size.
     *
     * @param <T> the generic type
     * @param size number of matched {@link WebElement} instances
     * @return predicate that returns true if it has the exact size
     */
    public static <T extends Elements> Predicate<T> forSize(final int size) {
        return new Predicate<T>() {
            @Override
            public boolean apply(T input) {
                return input.as(BasicElements.class).size() == size;
            }
        };
    }

    /**
     * Predicate to use with {@link WaitWebElements#wait(Predicate)} methods which ensures that
     * evaluation will only be successful when this instance is not empty (that is, evaluates
     * to one or more {@link WebElement} instances.
     *
     * @param <T> the generic type
     * @return predicate that returns true if it is empty
     */
    public static <T extends Elements> Predicate<T> forUnexistence() {
        return WaitPredicates.<T>forSize(0);
    }

    /**
     * Predicate to use with {@link WaitWebElements#wait(Predicate)} methods which ensures that
     * evaluation will only be successful when this instance is empty (that is, evaluates
     * to zero {@link org.openqa.selenium.WebElement} instances).
     *
     * @param <T> the generic type
     * @return predicate that returns true if it is empty
     */
    public static <T extends Elements> Predicate<T> forExistence() {
        return Predicates.not(WaitPredicates.<T>forUnexistence());
    }

}
