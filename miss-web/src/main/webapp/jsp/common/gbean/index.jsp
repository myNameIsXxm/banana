<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>${title}</title>
<%@ include file="/WEB-INF/pages/include/head.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/package/myDataGrid.js"></script>
</head>
<body>
<div id="conetnt_div" class="easyui-layout" fit="true" 
	data-options="region:'center',border:false" 
	style="padding-top:4px;height:auto;">
	<div id="table_div" class="easyui-layout" fit="true">
		<table id="content_table" class="easyui-datagrid">
		</table>
	</div>
</div>

<script type="text/javascript">
var table;
$(document).ready(function(){
	$.ajax({
		url : "<%=request.getContextPath()%>/body/${entity}/1/${pageSize}/$",
		type : "post",
		beforeSend : function() {
			showProgress();
		},
		complete : function() {
			closeProgress();
		},
		success : function(data2) {
			if(data2.hasOwnProperty("errMsg")){
				closeProgress();
				alert(data2.errMsg);
				return false;
			}
			$.ajax({
				url : "<%=request.getContextPath()%>/head/${entity}/$",
				type : "post",
				sync:false,
				success : function(data4) {
					if(data4.hasOwnProperty("errMsg")){
						closeProgress();
						alert(data4.errMsg);
						return false;
					}
					table = new myDataGrid({},{
						click:function(num, size){
							flush(num,size);
						}
					},"content_table",packageHead(data4));
					table.load(data2);
				}
			});
		}
	});
});

function packageHead(data){
	var col_total = 0;
	$.each(data[0],function(i,v){
		col_total+=v.colspan;
	});
	var width_head = 100/col_total;
	width_head = width_head.toFixed(2);
	var result = [];
	$.each(data,function(i,v){
		result[i]=packageOne(v,width_head);
	});
	return result;
}

function packageOne(data,width_head){
	var thead = [];
	$.each(data,function(i,v){
		thead[i] = {
			field : v.columnName,
			title : v.realName,
			halign : "center",
			align : "left",
			width : width_head+'%',
			colspan:v.colspan,
			rowspan:v.rowspan
		};
	});
	return thead;
}

function flush(num,size){
	$.ajax({
		url : "<%=request.getContextPath()%>/body/${entity}/"+num+"/"+size+"/$",
		type : "post",
		beforeSend : function() {
			showProgress();
		},
		complete : function() {
			closeProgress();
		},
		success : function(data) {
			if(data.hasOwnProperty("errMsg")){
				closeProgress();
				alert(data.errMsg);
				return false;
			}
			table.load(data);
		}
	});
}
</script>
</body>
</html>