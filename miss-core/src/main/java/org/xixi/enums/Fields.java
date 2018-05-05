package org.xixi.enums;

public interface Fields{
	String F_USER = "current_user";
	Integer PAGE_SIZE = 20; // extends.js myDataGrid.js 都需要做相应的修改
	String FORMAT_NUM = "0.00";
	String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
	String FORMAT_DATE_SQL = "yyyy-mm-dd HH24:mi:ss";
	Integer CACHE_SIZE = 100;
	Integer SESSION_INTERVAL = 20*60; // 查询到提交的间隔（单位/秒）
}
