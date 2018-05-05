package org.xixi.dao.face;

import java.util.List;
import java.util.Map;

import org.xixi.model.PageModel;

public interface BaseDaoFace {
	public Integer queryToInt(String sql, Map<String, Object> map) throws Exception;

	public PageModel queryToPageModel(String sql, Map<String, Object> map) throws Exception;

	public PageModel queryToPageModel(String sql, String sql2, Map<String, Object> map) throws Exception;

	public PageModel queryToPageModelForSql(String sql, Integer num, Integer size, String... map) throws Exception;

	public PageModel queryToPageModelForSql(String sql, String sql2, Integer num, Integer size, String... map)
			throws Exception;

	public List<Map<String, Object>> queryToMap(String sql, Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> queryToMapForSql(String sql, String... data) throws Exception;

	public void query(String sql, Map<String, Object> map) throws Exception;

	public void queryForSql(String sql, String... args) throws Exception;

	public Integer queryToIntForSql(String sql, Object... args) throws Exception;

	public String queryToStringForSql(String sql, Object... data) throws Exception;

	public PageModel queryToPageModelWithCache(String key, String sql_, Integer num, Integer size,
			Map<String, Object> map) throws Exception;

	public PageModel queryToPageModelForSqlWithCache(String key, String sql_, Integer num, Integer size, String... data)
			throws Exception;

	public Map<String, List<Map<String, Object>>> queryToMapWithTwoResult(String sql, Map<String, Object> map)
			throws Exception;
}
