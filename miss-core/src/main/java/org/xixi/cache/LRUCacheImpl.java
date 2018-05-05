package org.xixi.cache;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xixi.enums.Fields;
import org.xixi.utils.ConfigUtils;

public class LRUCacheImpl implements Cache {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String id = null;
	private Map<String, Object> cache = null;

	// for metrics
	protected double hits = 0;
	protected double misses = 0;
	protected double puts = 0;
	protected double removes = 0;
	protected Date startTime = new Date();

	@SuppressWarnings("unchecked")
	protected LRUCacheImpl(String id) {
		this.id = id;
		this.cache = Collections.synchronizedMap(new LRULinkedHashMap(ConfigUtils.getIntProperty("cache.size")));
	}

	@SuppressWarnings("unchecked")
	protected LRUCacheImpl(String id, int maxsize) {
		this.id = id;
		if (maxsize <= 0) {
			maxsize = Fields.CACHE_SIZE;
		}
		this.cache = Collections.synchronizedMap(new LRULinkedHashMap(maxsize));//synchronized 同步
	}

	public String getId() {
		return this.id;
	}

	public synchronized void put(String key, Object value) {
		this.cache.put(key, value);
		puts++;
	}

	public synchronized Object get(String key) {

		Object obj = this.cache.get(key);

		// for metrics
		if (obj == null) {
			logger.debug("MISSING [" + key + "]");
			misses++;
		} else {
			hits++;
		}

		return obj;
	}

	public synchronized void remove(String key) {

		this.cache.remove(key);
		removes++;
	}

	public synchronized void clear() {

		this.cache.clear();

		// clear metrics
		hits = 0;
		misses = 0;
		puts = 0;
		removes = 0;
		startTime = new Date();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getStats() {
		Map stats = new HashMap();
		stats.put("startTime", this.startTime);
		stats.put("hits", this.hits);
		stats.put("misses", this.misses);
		stats.put("puts", this.puts);
		stats.put("removes", this.removes);

		// calculate efficiency
		if ((misses - removes) > 0) {
			double efficiency = hits / (misses + hits);
			stats.put("efficiency", efficiency * 100);
		}

		return stats;
	}

	@SuppressWarnings("rawtypes")
	private static class LRULinkedHashMap extends LinkedHashMap {
		private static final long serialVersionUID = 1L;
		protected int maxsize;

		public LRULinkedHashMap(int maxsize) {
			super(maxsize * 4 / 3 + 1, 0.75f, true);
			this.maxsize = maxsize;
		}
		
		@Override
		protected boolean removeEldestEntry(Map.Entry eldest) {
			return this.size() > this.maxsize;
		}
	}

}
