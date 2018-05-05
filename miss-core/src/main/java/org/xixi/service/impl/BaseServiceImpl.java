package org.xixi.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;
import org.xixi.dao.face.BaseDaoFace;
import org.xixi.model.PageModel;
import org.xixi.service.face.BaseServiceFace;

@Component("baseService")
public class BaseServiceImpl implements BaseServiceFace {

	@Inject
	private BaseDaoFace baseDao;

	@Override
	public Integer queryToInt(String sql, Map<String, Object> map) throws Exception{
		return baseDao.queryToInt(sql, map);
	}

	@Override
	public PageModel queryToPageModel(String sql, Map<String, Object> map) throws Exception{
		return baseDao.queryToPageModel(sql, map);
	}

	@Override
	public PageModel queryToPageModel(String sql1, String sql2,
			Map<String, Object> map) throws Exception{
		return baseDao.queryToPageModel(sql1, sql2, map);
	}

	@Override
	public PageModel queryToPageModelForSql(String sql, Integer num,
			Integer size, String... map) throws Exception{
		return baseDao.queryToPageModelForSql(sql, num, size, map);
	}

	@Override
	public PageModel queryToPageModelForSql(String sql1, String sql2,
			Integer num, Integer size, String... map) throws Exception{
		return baseDao.queryToPageModelForSql(sql1, sql2, num, size, map);
	}

	@Override
	public List<Map<String, Object>> queryToMap(String sql,
			Map<String, Object> map) throws Exception{
		return baseDao.queryToMap(sql, map);
	}

	public List<Map<String, Object>> queryToMapForSql(String sql) throws Exception{
		return baseDao.queryToMapForSql(sql);
	}

	public List<Map<String, Object>> queryToMapForSql(String sql,
			String... data) throws Exception{
		return baseDao.queryToMapForSql(sql, data);
	}

	@Override
	public void query(String sql, Map<String, Object> map) throws Exception{
		baseDao.query(sql, map);
	}

	@Override
	public void queryForSql(String sql, String... args) throws Exception{
		baseDao.queryForSql(sql, args);
	}

	@Override
	public Integer queryToIntForSql(String sql, Object... data) throws Exception{
		return baseDao.queryToIntForSql(sql, data);
	}

	@Override
	public String queryToStringForSql(String sql, Object... args) throws Exception{
		return baseDao.queryToStringForSql(sql, args);
	}

	@Override
	public PageModel queryToPageModelWithCache(String key, String sql_,
			Integer num, Integer size, Map<String, Object> map) throws Exception{
		return baseDao.queryToPageModelWithCache(key, sql_, num, size, map);
	}

	@Override
	public PageModel queryToPageModelForSqlWithCache(String key, String sql_,
			Integer num, Integer size, String... data) throws Exception{
		return baseDao.queryToPageModelForSqlWithCache(key, sql_, num, size,
				data);
	}

	@Override
	public Map<String, List<Map<String, Object>>> queryToMapWithTwoResult(String sql, Map<String, Object> map) throws Exception{
		return baseDao.queryToMapWithTwoResult( sql,  map);
	}

}
