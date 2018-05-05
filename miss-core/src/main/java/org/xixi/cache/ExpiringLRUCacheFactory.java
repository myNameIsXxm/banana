package org.xixi.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xixi.utils.ConfigUtils;

public class ExpiringLRUCacheFactory implements CacheFactory {

	private static final Logger logger = LoggerFactory.getLogger(ExpiringLRUCacheFactory.class);

	protected ExpiringLRUCacheFactory() {
	}

	@Override
	public Cache createCache(String id) {
		int size = ConfigUtils.getIntProperty("cache.size");
		long timeout = ConfigUtils.getIntProperty("cache.timeout");
		Cache cache = new ExpiringLRUCacheImpl(id, size, timeout);
		logger.debug("new cache constructed. size=" + size + ", timeout=" + timeout);
		return cache;
	}

	@Override
	public Cache createCache(String id, int size, long timeout) {
		Cache cache = new ExpiringLRUCacheImpl(id, size, timeout);
		logger.debug("new cache constructed. size=" + size + ", timeout=" + timeout);
		return cache;
	}
}
