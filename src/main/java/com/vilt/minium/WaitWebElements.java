package com.vilt.minium;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Predicate;

/**
 * 
 * @author Rui
 *
 * @param <T>
 */
public interface WaitWebElements<T extends WebElements> extends WebElements {
	
	/**
	 * 
	 * @param predicate
	 * @return
	 */
	public T wait(Predicate<? super T> predicate);

	/**
	 * 
	 * @param time
	 * @param unit
	 * @param predicate
	 * @return
	 */
	public T wait(long time, TimeUnit unit, Predicate<? super T> predicate);

	/**
	 * 
	 * @param predicate
	 * @return
	 */
	public T waitOrTimeout(Predicate<? super T> predicate);
	
	/**
	 * 
	 * @param time
	 * @param unit
	 * @param predicate
	 * @return
	 */
	public T waitOrTimeout(long time, TimeUnit unit, Predicate<? super T> predicate);
}
