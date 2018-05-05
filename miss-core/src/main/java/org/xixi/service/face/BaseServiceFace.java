package org.xixi.service.face;

import java.util.List;
import java.util.Map;

import org.xixi.model.PageModel;

public interface BaseServiceFace {
	public Integer queryToInt(String sql, Map<String, Object> map) throws Exception;

	public PageModel queryToPageModel(String sql, Map<String, Object> map) throws Exception;

	public PageModel queryToPageModel(String sql1, String sql2, Map<String, Object> map) throws Exception;

	public PageModel queryToPageModelForSql(String sql, Integer num, Integer size, String... map) throws Exception;

	public PageModel queryToPageModelForSql(String sql1, String sql2, Integer num, Integer size, String... map)
			throws Exception;

	public List<Map<String, Object>> queryToMap(String sql, Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryToMapForSql(String sql) throws Exception;

	public List<Map<String, Object>> queryToMapForSql(String sql, String... data) throws Exception;

	public void query(String sql, Map<String, Object> map) throws Exception;

	public void queryForSql(String sql, String... objects) throws Exception;

	public Integer queryToIntForSql(String string, Object... objects) throws Exception;

	public String queryToStringForSql(String string, Object... objects) throws Exception;

	public PageModel queryToPageModelWithCache(String key, String sql_, Integer num, Integer size,
			Map<String, Object> map) throws Exception;

	public PageModel queryToPageModelForSqlWithCache(String key, String sql_, Integer num, Integer size, String... data)
			throws Exception;

	public Map<String, List<Map<String, Object>>> queryToMapWithTwoResult(String sql, Map<String, Object> map)
			throws Exception;
}