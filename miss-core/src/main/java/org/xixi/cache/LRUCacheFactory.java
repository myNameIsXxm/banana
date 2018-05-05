package org.xixi.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xixi.enums.Fields;

public class LRUCacheFactory implements CacheFactory {

	private static final Logger logger = LoggerFactory.getLogger(LRUCacheFactory.class);

	protected LRUCacheFactory() {
	}

	/**
	 * Construct a new instance of a LRUCache.
	 */
	@Override
	public Cache createCache(String id) {
		Cache cache = new LRUCacheImpl(id, Fields.CACHE_SIZE);
		logger.debug("new cache constructed. size=" + Fields.CACHE_SIZE);
		return cache;
	}

	@Override
	public Cache createCache(String id, int size, long timeout) {
		Cache cache = new LRUCacheImpl(id, size);
		logger.debug("new cache constructed. size=" + size);
		return cache;
	}
}
