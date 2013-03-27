package com.vilt.minium;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Predicate;

/**
 * The Interface WaitWebElements.
 *
 * @param <T> the generic type
 * @author Rui
 */
public interface WaitWebElements<T extends WebElements> extends WebElements {
	
	/**
	 * Wait.
	 *
	 * @param predicate the predicate
	 * @return the t
	 */
	public T wait(Predicate<? super T> predicate);

	/**
	 * Wait.
	 *
	 * @param time the time
	 * @param unit the unit
	 * @param predicate the predicate
	 * @return the t
	 */
	public T wait(long time, TimeUnit unit, Predicate<? super T> predicate);

	public T wait(Duration timeout, Predicate<? super T> predicate);

	/**
	 * Wait or timeout.
	 *
	 * @param predicate the predicate
	 * @return the t
	 */
	public T waitOrTimeout(Predicate<? super T> predicate);
	
	/**
	 * Wait or timeout.
	 *
	 * @param time the time
	 * @param unit the unit
	 * @param predicate the predicate
	 * @return the t
	 */
	public T waitOrTimeout(long time, TimeUnit unit, Predicate<? super T> predicate);
	
	public T waitOrTimeout(Duration timeout, Predicate<? super T> predicate);
}
