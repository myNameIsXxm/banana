package org.xixi.cache;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xixi.enums.Fields;

/**
 * 2017年05月22号
 * 
 * @author XiXiaoMing
 */
public class LRUCache {
	private static LRUCache instance;

	public static LRUCache getInstance() {
		if (instance == null) {
			synchronized (LRUCache.class) {
				if (instance == null) {
					instance = new LRUCache();
				}
			}
		}
		return instance;
	}

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private Map<String, Object> cache = null;

	protected double hits = 0; // 命中个数（热度）
	protected double misses = 0; // 不命中个数
	protected double puts = 0; // 容器内添加的对象个数
	protected double removes = 0; // 容器内移除的对象个数
	protected Date startTime = new Date();
	
	 @SuppressWarnings("unchecked") 
	 private LRUCache() { 
		 this.cache =Collections.synchronizedMap(new LRULinkedHashMap(Fields.CACHE_SIZE));
	 }
	 

	public synchronized void put(String key, Object value) {
		this.cache.put(key, value);
		puts++;
	}

	public synchronized Object get(String key) {

		Object obj = this.cache.get(key);

		if (obj == null) {
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
		if (misses + hits != 0) {
			stats.put("efficiency", hits * 100 / (misses + hits) + "%");
		}
		stats.put("data", cache.keySet());
		return stats;
	}

	@SuppressWarnings("rawtypes")
	private static class LRULinkedHashMap extends LinkedHashMap {
		private static final long serialVersionUID = 1L;
		protected int maxsize;

		public LRULinkedHashMap(int maxsize) {
			super(maxsize * 4 / 3 + 1, 0.75f, true); // true：基于访问排序
			this.maxsize = maxsize;
		}

		@Override
		protected boolean removeEldestEntry(Map.Entry eldest) {
			return this.size() > this.maxsize;
		}
	}

}
