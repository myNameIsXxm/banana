package org.xixi.jdbc.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCreator;

import oracle.jdbc.OracleTypes;

public class CreatorImplWithCache implements CallableStatementCreator {
	private String sql;
	private Map<String, Object> map;

	public CreatorImplWithCache(String sql, Map<String, Object> map) {
		this.sql = sql;
		this.map = map;
	}

	@Override
	public CallableStatement createCallableStatement(Connection con) throws SQLException {
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
		CallableStatement cs = con.prepareCall(storedProc);
		if (map != null && map.size() > 0) {
			for (String s : map.keySet()) {
				cs.setObject(s, map.get(s));
			}
		}
		cs.registerOutParameter("CUR_RESULT", OracleTypes.CURSOR);
		return cs;
	}
}
