package com.vilt.minium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;

import com.google.common.base.Predicate;
import com.vilt.minium.driver.Configuration;

/**
 * Provides wait methods for {@link WebElements}.
 *
 * @param <T> {@link WebElements} subclass
 * @author rui.figueira
 */
public interface WaitWebElements<T extends WebElements> extends WebElements {

	/**
	 * Waits using the default timeout value provided by {@link Configuration#getDefaultInterval()}
	 * for this {@link WebElements} to satisfy a given predicate, or throws a.
	 *
	 * @param predicate the predicate to wait for.
	 * @return a {@link WebElements}
	 * {@link TimeoutException} otherwise.
	 */	public T wait(Predicate<? super T> predicate);

	/**
	 * Waits with a specified timeout for this {@link WebElements} to satisfy a given predicate,
	 * or throws a {@link TimeoutException} otherwise.
	 *
	 * @param time the timeout time
	 * @param unit the timeout unit
	 * @param predicate the predicate to wait for.
	 * @return a {@link WebElements}
	 */
	public T wait(long time, TimeUnit unit, Predicate<? super T> predicate);

	/**
	 * Waits with a specified timeout for this {@link WebElements} to satisfy a given predicate,
	 * or throws a {@link TimeoutException} otherwise.
	 *
	 * @param timeout the timeout. If null, it will use the default timeout value provided by
	 * @param predicate the predicate to wait for.
	 * @return a {@link WebElements}
	 * {@link Configuration#getDefaultInterval()}.
	 */
	public T wait(Duration timeout, Predicate<? super T> predicate);

	/**
	 * Waits using the default timeout value provided by {@link Configuration#getDefaultInterval()}
	 * for this {@link WebElements} to satisfy a given predicate, otherwise it just returns.
	 * 
	 * @param predicate the predicate to wait for.
	 * @return a {@link WebElements}
	 */
	public T waitOrTimeout(Predicate<? super T> predicate);
	
	/**
	 * Waits with a specified timeout for this {@link WebElements} to satisfy a given predicate, 
	 * otherwise it just returns.
	 * 
	 * @param time the timeout time.
	 * @param unit the timeout unit.
	 * @param predicate the predicate to wait for.
	 * @return a {@link WebElements}
	 */
	public T waitOrTimeout(long time, TimeUnit unit, Predicate<? super T> predicate);
	
	/**
	 * Waits with a specified timeout for this {@link WebElements} to satisfy a given predicate,
	 * otherwise it just returns.
	 *
	 * @param timeout the timeout. If null, it will use the default timeout value provided by
	 * @param predicate the predicate to wait for.
	 * @return a {@link WebElements}
	 * {@link Configuration#getDefaultInterval()}.
	 */
	public T waitOrTimeout(Duration timeout, Predicate<? super T> predicate);
}
