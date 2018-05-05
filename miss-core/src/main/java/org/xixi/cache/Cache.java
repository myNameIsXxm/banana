package org.xixi.cache;

import java.util.Map;

public interface Cache {

	public String getId();

	public void put(String key, Object value);

	public Object get(String key);

	public void remove(String key);

	public void clear();

	@SuppressWarnings("rawtypes")
	public Map getStats();

}
