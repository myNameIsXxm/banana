package org.xixi.jdbc.base;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.xixi.dao.impl.BaseDaoUtils;

public class CallbackImpl implements CallableStatementCallback<List<Map<String, Object>>> {
	private Integer total;

	@Override
	public List<Map<String, Object>> doInCallableStatement(CallableStatement cs)
			throws SQLException, DataAccessException {
		List<Map<String, Object>> resultsMap = new ArrayList<Map<String, Object>>();
		cs.execute();
		ResultSet rs = (ResultSet) cs.getObject("CUR_RESULT");// 获取游标一行的值
		while (rs.next()) {
			resultsMap.add(BaseDaoUtils.getResultMap(rs));
		}
		rs.close();
		setTotal(cs.getInt("PI_TOTAL"));
		return resultsMap;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}