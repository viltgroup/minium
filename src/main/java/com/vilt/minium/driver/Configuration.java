package com.vilt.minium.driver;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Objects;

public class Configuration implements Serializable {

	public static class Duration implements Serializable {

		private static final long serialVersionUID = 6346769955278974561L;

		private final long time;
		private final TimeUnit unit;

		public Duration(long time, TimeUnit unit) {
			checkArgument(time > 0);
			checkNotNull(unit);
			this.time = time;
			this.unit = unit;
		}

		public long getTime() {
			return time;
		}

		public TimeUnit getUnit() {
			return unit;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Duration) {
				Duration other = (Duration) o;
				return this.time == other.time && this.unit == other.unit;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(time, unit);
		}

		@Override
		public String toString() {
			return String.format("%d %s", time, unit);
		}
	}
	
	private static final long serialVersionUID = -6096136074620998211L;

	private Duration defaultTimeout = new Duration(10, TimeUnit.SECONDS);
	private Duration defaultInterval = new Duration(1, TimeUnit.SECONDS);
	
	public Duration getDefaultTimeout() {
		return defaultTimeout;
	}

	public Configuration defaultTimeout(Duration defaultTimeout) {
		checkNotNull(defaultTimeout);
		this.defaultTimeout = defaultTimeout;
		return this;
	}

	public Configuration defaultTimeout(long time, TimeUnit unit) {
		return defaultTimeout(new Duration(time, unit));
	}

	public Duration getDefaultInterval() {
		return defaultInterval;
	}

	public Configuration defaultInterval(Duration defaultInterval) {
		checkNotNull(defaultInterval);
		this.defaultInterval = defaultInterval;
		return this;
	}

	public Configuration defaultInterval(long time, TimeUnit unit) {
		return defaultInterval(new Duration(time, unit));
	}

}