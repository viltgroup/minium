package com.vilt.minium.driver;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Objects;

/**
 * The Class Configuration.
 */
public class Configuration implements Serializable {

	/**
	 * The Class Duration.
	 */
	public static class Duration implements Serializable {

		private static final long serialVersionUID = 6346769955278974561L;

		private final long time;
		private final TimeUnit unit;

		/**
		 * Instantiates a new duration.
		 *
		 * @param time the time
		 * @param unit the unit
		 */
		public Duration(long time, TimeUnit unit) {
			checkArgument(time > 0);
			checkNotNull(unit);
			this.time = time;
			this.unit = unit;
		}

		/**
		 * Gets the time.
		 *
		 * @return the time
		 */
		public long getTime() {
			return time;
		}

		/**
		 * Gets the unit.
		 *
		 * @return the unit
		 */
		public TimeUnit getUnit() {
			return unit;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object o) {
			if (o instanceof Duration) {
				Duration other = (Duration) o;
				return this.time == other.time && this.unit == other.unit;
			}
			return false;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(time, unit);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return String.format("%d %s", time, unit);
		}
	}
	
	private static final long serialVersionUID = -6096136074620998211L;

	private Duration defaultTimeout = new Duration(10, TimeUnit.SECONDS);
	private Duration defaultInterval = new Duration(1, TimeUnit.SECONDS);
	
	/**
	 * Gets the default timeout.
	 *
	 * @return the default timeout
	 */
	public Duration getDefaultTimeout() {
		return defaultTimeout;
	}

	/**
	 * Default timeout.
	 *
	 * @param defaultTimeout the default timeout
	 * @return the configuration
	 */
	public Configuration defaultTimeout(Duration defaultTimeout) {
		checkNotNull(defaultTimeout);
		this.defaultTimeout = defaultTimeout;
		return this;
	}

	/**
	 * Default timeout.
	 *
	 * @param time the time
	 * @param unit the unit
	 * @return the configuration
	 */
	public Configuration defaultTimeout(long time, TimeUnit unit) {
		return defaultTimeout(new Duration(time, unit));
	}

	/**
	 * Gets the default interval.
	 *
	 * @return the default interval
	 */
	public Duration getDefaultInterval() {
		return defaultInterval;
	}

	/**
	 * Default interval.
	 *
	 * @param defaultInterval the default interval
	 * @return the configuration
	 */
	public Configuration defaultInterval(Duration defaultInterval) {
		checkNotNull(defaultInterval);
		this.defaultInterval = defaultInterval;
		return this;
	}

	/**
	 * Default interval.
	 *
	 * @param time the time
	 * @param unit the unit
	 * @return the configuration
	 */
	public Configuration defaultInterval(long time, TimeUnit unit) {
		return defaultInterval(new Duration(time, unit));
	}

}