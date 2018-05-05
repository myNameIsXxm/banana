package org.xixi.model;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PageModel {
	private Integer total;
	private String key;
	private List<Map<String,Object>> rows;
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public static void format(List<Map<String,Object>> rows){
		for(Map<String, Object> row:rows){
			for(String s:row.keySet()){
				Object obj = row.get(s);
				if(obj instanceof Clob){
					StringBuffer str=new StringBuffer("");
					try {
						int y;
						char ac[]=new char[4096];
						Reader reader = ((Clob)obj).getCharacterStream();
						while((y=reader.read(ac,0,4096))!=-1){
							str.append(new String(ac,0,y));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					row.put(s,str.toString());
				}
			}
		}
	}
	
	public List<Map<String, Object>> getRows() {
		format(rows);
		return rows;
	}
	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}
}
