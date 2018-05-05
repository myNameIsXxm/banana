package org.xixi.cache;

import org.xixi.utils.ConfigUtils;

public class ExpiringLRUCacheImpl extends LRUCacheImpl {

	private long timeout = 0;

	protected ExpiringLRUCacheImpl(String id) {
		super(id);
		this.timeout =ConfigUtils.getIntProperty("cache.timeout")* 1000;
	}

	protected ExpiringLRUCacheImpl(String id, int maxsize, long timeout) {
		super(id, maxsize);
		if (timeout <= 0) {
			timeout = ConfigUtils.getIntProperty("cache.timeout");
		}
		this.timeout = timeout * 1000;
	}

	/**
	 * Store an entry in the cache.
	 * <p/>
	 * We wrap the cached object in our ExpiringCacheEntry object so that we can
	 * track when the entry has expired.
	 */
	public synchronized void put(String key, Object value) {
		ExpiringCacheEntry entry = new ExpiringCacheEntry(value, this.timeout);
		super.put(key, entry);
	}

	/**
	 * Retrieve an entry from the cache.
	 * <p/>
	 * This LRU cache supports timeouts, so if the cached object has expired
	 * then we return null, just as if the entry wasn't found.
	 */
	public Object get(String key) {

		Object value = null;
		ExpiringCacheEntry entry;

		synchronized (this) {
			entry = (ExpiringCacheEntry) super.get(key);
		}

		if (entry != null) {

			value = entry.getValue();

			// if the value is null then that means this entry expired
			if (value == null) {
				logger.debug("EXPIRED [" + key + "]");
				hits--;
				super.remove(key);
			}
		}

		return value;
	}
}
