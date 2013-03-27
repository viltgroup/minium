package com.vilt.minium;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Objects;

/**
 * The Class Duration.
 */
public class Duration implements Serializable {

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
		checkArgument(time >= 0);
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