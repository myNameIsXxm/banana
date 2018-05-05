package org.xixi.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDaoUtils {
	public static Map<String, Object> getResultMap(ResultSet rs) throws SQLException {
		Map<String, Object> hm = new HashMap<String, Object>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		for (int i = 1; i <= count; i++) {
			String key = rsmd.getColumnLabel(i);
			String value = rs.getString(i);
			hm.put(key, value);
		}
		return hm;
	}
	
	public static List<Map<String, Object>> getPageRows(List<Map<String, Object>> list, int cur, int size) {
		int max = cur*size;
		int min = size * (cur - 1);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(max>list.size()){
			max = list.size();
		}
		for(int i=min;i<max;i++){
			result.add(list.get(i));
		}
		return result;
	}
}
