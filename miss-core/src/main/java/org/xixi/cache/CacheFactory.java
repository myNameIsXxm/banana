package org.xixi.cache;

public interface CacheFactory {

	/**
	 * 构�?�一个Chache
	 * @param id key�?
	 */
	public Cache createCache(String id);

	/**
	 * 构�?�一个Chache
	 * @param id key �?
	 * @param size 缓存长度（可以存几个数据�?
	 * @param timeout 过期时间（单位s�?
	 */
	public Cache createCache(String id, int size, long timeout);

	/**
	 * 此缓存不会到�?
	 */
	static final CacheFactory FACTORY = new LRUCacheFactory();
	
	/**
	 * 此缓存会到期
	 */
	static final CacheFactory EXPIRING_FACTORY = new ExpiringLRUCacheFactory();
	
}
