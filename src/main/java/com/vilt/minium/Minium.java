package com.vilt.minium;

import static com.google.common.base.Predicates.not;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.vilt.minium.driver.DefaultWebElementsDriver;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.jquery.DefaultWebElements;

/**
 * The Class Minium.
 *
 * @author Rui
 */
public class Minium {

	/**
	 * Predicate to use with {@link WebElements#wait(Predicate)} methods which ensures that
	 * evaluation will only be successful when this instance is empty (that is, evaluates
	 * to zero {@link WebElement} instances.
	 *
	 * @param <T> the generic type
	 * @return predicate that returns true if it is empty
	 */
	public static <T extends WaitWebElements<?>> Predicate<T> untilEmpty() {
		return new Predicate<T>() {
			public boolean apply(T input) {
				return Iterables.isEmpty(input);
			}
		};
	}
	
	/**
	 * Predicate to use with {@link WebElements#wait(Predicate)} methods which ensures that
	 * evaluation will only be successful when this instance is not empty (that is, evaluates
	 * to one or more {@link WebElement} instances.
	 *
	 * @param <T> the generic type
	 * @return predicate that returns true if it is empty
	 */
	public static <T extends WaitWebElements<?>> Predicate<T> untilNotEmpty() {
		return not(Minium.<T>untilEmpty());
	}
	
	/**
	 * Predicate to use with {@link WebElements#wait(Predicate)} methods which ensures that
	 * evaluation will only be successful when this instance has a specific size.
	 *
	 * @param <T> the generic type
	 * @param size number of matched {@link WebElement} instances
	 * @return predicate that returns true if it has the exact size
	 */
	public static  <T extends WaitWebElements<?>> Predicate<T> untilSize(final int size) {
		return new Predicate<T>() {
			public boolean apply(T input) {
				return Iterables.size(input) == size;
			}
		};
	}
	
	/**
	 * $.
	 *
	 * @param <T> the generic type
	 * @param wd the wd
	 * @return the t
	 */
	public static <T extends WebElements> T $(WebElementsDriver<T> wd) {
		return wd.webElements();
	}
	
	/**
	 * $.
	 *
	 * @param <T> the generic type
	 * @param wd the wd
	 * @param selector the selector
	 * @return the t
	 */
	public static <T extends WebElements> T $(WebElementsDriver<T> wd, String selector) {
		return wd.webElements(selector);
	}
	
	/**
	 * $.
	 *
	 * @param wd the wd
	 * @return the default web elements
	 */
	public static DefaultWebElements $(WebDriver wd) {
		return $(new DefaultWebElementsDriver(wd));
	}

}