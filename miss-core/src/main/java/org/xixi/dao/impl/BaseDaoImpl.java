package org.xixi.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xixi.cache.LRUCache;
import org.xixi.dao.face.BaseDaoFace;
import org.xixi.jdbc.base.CallbackImpl;
import org.xixi.jdbc.base.CallbackImplWithCache;
import org.xixi.jdbc.base.CreatorImpl;
import org.xixi.jdbc.base.CreatorImplWithCache;
import org.xixi.model.PageModel;

import oracle.jdbc.OracleTypes;

@Component("baseDao")
@Transactional(transactionManager = "transactionManager")
public class BaseDaoImpl extends BaseDao implements BaseDaoFace {

	private static final Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);

	@Override
	public Integer queryToInt(String sql, Map<String, Object> map) throws Exception {
		String storedProc = "";
		if (map == null) {
			storedProc = "{" + sql + "(?)}";
		} else {
			storedProc = "{" + sql + "(?";
			for (int i = 0; i < map.size(); i++) {
				storedProc += ",?";
			}
			storedProc += ")}";
		}
		int result = 0;
		CallableStatement cs = getSession().connection().prepareCall(storedProc);
		cs.registerOutParameter("PI_BZ", OracleTypes.INTEGER);
		if (map != null && map.size() > 0) {
			for (String s : map.keySet()) {
				cs.setObject(s, map.get(s));
			}
		}
		cs.execute();
		result = cs.getInt("PI_BZ");
		return result;
	}

	public List<Map<String, Object>> queryToMap(String sql, Map<String, Object> map) throws Exception {
		String storedProc = "";
		if (map == null) {
			storedProc = "{" + sql + "(?)}";
		} else {
			storedProc = "{" + sql + "(?";
			for (int i = 0; i < map.size(); i++) {
				storedProc += ",?";
			}
			storedProc += ")}";
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		CallableStatement cs = getSession().connection().prepareCall(storedProc);
		cs.registerOutParameter("CUR_RESULT", OracleTypes.CURSOR);
		if (map != null && map.size() > 0) {
			for (String s : map.keySet()) {
				cs.setObject(s, map.get(s));
			}
		}
		cs.execute();
		ResultSet rs = (ResultSet) cs.getObject("CUR_RESULT");
		if (rs != null) {
			ResultSetMetaData md = rs.getMetaData();
			while (rs.next()) {
				Map<String, Object> m = new HashMap<String, Object>();
				for (int i = 0; i < md.getColumnCount(); i++) {
					m.put(md.getColumnName(i + 1), rs.getObject(i + 1));
				}
				result.add(m);
			}
		}
		return result;
	}

	public Map<String, List<Map<String, Object>>> queryToMapWithTwoResult(String sql, Map<String, Object> map)
			throws Exception {
		String storedProc = "";
		if (map == null) {
			storedProc = "{" + sql + "(?,?)}";
		} else {
			storedProc = "{" + sql + "(?,?";
			for (int i = 0; i < map.size(); i++) {
				storedProc += ",?";
			}
			storedProc += ")}";
		}
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> result2 = new ArrayList<Map<String, Object>>();
		CallableStatement cs = getSession().connection().prepareCall(storedProc);
		cs.registerOutParameter("CUR_RESULT1", OracleTypes.CURSOR);
		cs.registerOutParameter("CUR_RESULT2", OracleTypes.CURSOR);
		if (map != null && map.size() > 0) {
			for (String s : map.keySet()) {
				cs.setObject(s, map.get(s));
			}
		}
		cs.execute();
		ResultSet rs1 = (ResultSet) cs.getObject("CUR_RESULT1");
		if (rs1 != null) {
			ResultSetMetaData md1 = rs1.getMetaData();
			while (rs1.next()) {
				Map<String, Object> m1 = new HashMap<String, Object>();
				for (int i = 0; i < md1.getColumnCount(); i++) {
					m1.put(md1.getColumnName(i + 1), rs1.getObject(i + 1));
				}
				result1.add(m1);
			}
		}
		ResultSet rs2 = (ResultSet) cs.getObject("CUR_RESULT2");
		if (rs2 != null) {
			ResultSetMetaData md2 = rs2.getMetaData();
			while (rs2.next()) {
				Map<String, Object> m2 = new HashMap<String, Object>();
				for (int i = 0; i < md2.getColumnCount(); i++) {
					m2.put(md2.getColumnName(i + 1), rs2.getObject(i + 1));
				}
				result2.add(m2);
			}
		}
		Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
		result.put("r1", result1);
		result.put("r2", result2);
		return result;
	}

	public List<Map<String, Object>> queryToMapForSql(String sql, String... data) throws Exception {
		SQLQuery query = getSession().createSQLQuery(sql);
		for (int i = 0; i < data.length; i++) {
			query.setString(i, data[i]);
		}
		List<Map<String, Object>> result = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return result;
	}

	@Override
	public PageModel queryToPageModel(String sql_, Map<String, Object> map) throws Exception {
		PageModel model = new PageModel();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Integer total = 0;
		sql_ = sql_.replace("exec ", "call ").replace("EXEC ", "call ").replace("CALL", "call");
		CallbackImpl callbackImpl = new CallbackImpl();
		result = jdbcTemplate.execute(new CreatorImpl(sql_, map), callbackImpl);
		total = callbackImpl.getTotal();
		model.setRows(result);
		model.setTotal(total);
		return model;
	}

	public PageModel queryToPageModelWithCache(String key, String sql_, Integer num, Integer size,
			Map<String, Object> map) throws Exception {
		LRUCache cache = LRUCache.getInstance();
		if (num > 1 && cache.get(key) != null) {
			log.info("获取缓存数据，第" + num + "页，主键：" + key);
			List<Map<String, Object>> list = (List<Map<String, Object>>) cache.get(key);
			PageModel model = new PageModel();
			model.setRows(BaseDaoUtils.getPageRows(list, num, size));
			model.setTotal(list.size());
			return model;
		}
		PageModel model = new PageModel();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Integer total = 0;
		sql_ = sql_.replace("exec ", "call ").replace("EXEC ", "call ").replace("CALL", "call");
		CallbackImplWithCache callbackImpl = new CallbackImplWithCache();
		result = jdbcTemplate.execute(new CreatorImplWithCache(sql_, map), callbackImpl);
		model.setRows(BaseDaoUtils.getPageRows(result, num, size));
		model.setTotal(result.size());
		if (key != null) {
			cache.put(key, result);
		}
		return model;
	}

	public PageModel queryToPageModelForSql(String sql_, Integer num, Integer size, String... data) throws Exception {
		PageModel model = new PageModel();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Integer total = 0;
		SQLQuery query = getSession().createSQLQuery(sql_);
		for (int i = 0; i < data.length; i++) {
			query.setString(i, data[i]);
		}
		query.setFirstResult(size * (num - 1));
		query.setMaxResults(size);
		result = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		total = jdbcTemplate.queryForList(sql_, data).size();
		model.setRows(result);
		model.setTotal(total);
		return model;
	}

	public PageModel queryToPageModelForSqlWithCache(String key, String sql_, Integer num, Integer size, String... data)
			throws Exception {
		LRUCache cache = LRUCache.getInstance();
		if (num > 1 && cache.get(key) != null) {
			log.info("获取缓存数据，第" + num + "页，主键：" + key);
			List<Map<String, Object>> list = (List<Map<String, Object>>) cache.get(key);
			PageModel model = new PageModel();
			model.setRows(BaseDaoUtils.getPageRows(list, num, size));
			model.setTotal(list.size());
			return model;
		}

		PageModel model = new PageModel();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Integer total = 0;
		SQLQuery query = getSession().createSQLQuery(sql_);
		for (int i = 0; i < data.length; i++) {
			query.setString(i, data[i]);
		}
		result = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		model.setRows(BaseDaoUtils.getPageRows(result, num, size));
		model.setTotal(result.size());
		cache.put(key, result);
		return model;
	}

	@Override
	public PageModel queryToPageModel(String sql1, String sql2, Map<String, Object> map) throws Exception {
		PageModel model = new PageModel();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		sql1 = sql1.replace("exec ", "call ").replace("EXEC ", "call ");
		Integer count = jdbcTemplate.queryForObject(sql2, Integer.class);
		CallbackImpl callbackImpl = new CallbackImpl();
		result = jdbcTemplate.execute(new CreatorImpl(sql1, map), callbackImpl);
		model.setRows(result);
		model.setTotal(count);
		return model;
	}

	public PageModel queryToPageModelForSql(String sql1, String sql2, Integer num, Integer size, String... data)
			throws Exception {
		PageModel model = new PageModel();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		sql1 = sql1.replace("exec ", "call ").replace("EXEC ", "call ");
		Integer count = jdbcTemplate.queryForObject(sql2, Integer.class);
		SQLQuery query = getSession().createSQLQuery(sql1);
		for (int i = 0; i < data.length; i++) {
			query.setString(i, data[i]);
		}
		query.setFirstResult(size * (num - 1));
		query.setMaxResults(size);
		result = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		model.setRows(result);
		model.setTotal(count);
		return model;
	}

	@Override
	public void query(String sql, Map<String, Object> map) throws Exception {
		String storedProc = "";
		if (map == null) {
			storedProc = "{" + sql + "()}";
		} else {
			storedProc = "{" + sql + "(";
			for (int i = 0; i < map.size(); i++) {
				storedProc += "?,";
			}
			if (storedProc.endsWith(",")) {
				storedProc = storedProc.substring(0, storedProc.length() - 1);
			}
			storedProc += ")}";
		}
		CallableStatement cs = getSession().connection().prepareCall(storedProc);
		if (map != null && map.size() > 0) {
			for (String s : map.keySet()) {
				cs.setObject(s, map.get(s));
			}
		}
		cs.execute();
	}

	@Override
	public void queryForSql(String sql, String... data) throws Exception {
		Query query = getSession().createSQLQuery(sql);
		for (int i = 0; i < data.length; i++) {
			query.setString(i, data[i]);
		}
		query.executeUpdate();
	}

	@Override
	public Integer queryToIntForSql(String sql, Object... args) throws Exception {
		Integer result = jdbcTemplate.queryForObject(sql, args, Integer.class);
		return result;
	}

	@Override
	public String queryToStringForSql(String sql, Object... args) throws Exception {
		return jdbcTemplate.queryForObject(sql, args, String.class);
	}

}