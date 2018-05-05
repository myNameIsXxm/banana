package org.xixi.cache;

import java.io.Serializable;

public class ExpiringCacheEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	private Object value;
	private long timeCached = -1;
	private long timeout = 0;

	public ExpiringCacheEntry(Object value, long timeout) {
		this.value = value;

		// don't support negative values
		if (timeout > 0) {
			this.timeout = timeout;
		}

		this.timeCached = System.currentTimeMillis();
	}

	public long getTimeCached() {
		return this.timeCached;
	}

	public long getTimeout() {
		return this.timeout;
	}

	public Object getValue() {
		if (this.hasExpired()) {
			return null;
		} else {
			return this.value;
		}
	}

	public boolean hasExpired() {

		long now = System.currentTimeMillis();

		return ((this.timeCached + this.timeout) < now);
	}
}
