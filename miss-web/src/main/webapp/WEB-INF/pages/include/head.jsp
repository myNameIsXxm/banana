<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.es.utils.Fields" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String version_default = "1.4.5"; // 1.4.5  1.3.5  1.5.2
	String theme_default = "metro-blue"; // black bootstrap default gray material metro metro-blue
%>
<head>
<c:set var="pageSize" value="<%=Fields.PAGE_SIZE%>"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui-<%=version_default%>/themes/<%=theme_default%>/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui-<%=version_default%>/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/JQuery-zTree-v3.5.15/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui-<%=version_default%>/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui-<%=version_default%>/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>js/JQuery-zTree-v3.5.15/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/extends.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>js/ie.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>js/common.js" charset="UTF-8"></script>
<script src="<%=basePath%>js/ajaxfileupload.js" type="text/javascript"></script>
<style>
	.datagrid-header td,
	.datagrid-body td,
	.datagrid-footer td {
	  border-color: #524F4F; 
	}
	.l-btn {
	  border-color:#7DF30A;
	}
	.select2-container.select2-container--classic.select2-container--open{
		z-index:9999;
	}
	body{
		margin:0px;
		padding:0px;
		overflow-y: scroll;
	}
</style>
<script type="text/javascript">
jQuery.support.cors=true;
var rootPath = "<%=request.getContextPath()%>";
var entity = "${entity}";
function showProgress() {
	$.messager.progress({
		title : '请稍后',
		msg : '获取数据中...'
	});
}
function closeProgress() {
	$.messager.progress('close');
}
window.alert=function(a){
	$.messager.alert({
		title:'提示信息',
		msg:a
	});
}
window.prompt= function(a,b){
	$.messager.prompt({
		title: '提示信息',
		msg: a,
		fn: function(r){
			if(r){
				b(r);
			}
		}
	});
}

window.confirm = function(a,b){
	$.messager.confirm({
		title: '提示信息',
		msg: a,
		fn: function(r){
			if (r){
				b();
			}
		}
	});
}

function headClick(id){
	if($("#"+id).prev(".panel-header").length!=0){
		$("#"+id).prev().click(function(){
			$("#"+id).prev().find(".panel-tool-collapse").eq(0).click();
		});
	}else{
		setTimeout(function(){
			headClick(id);
		},10);
	}
}
$(document).ready(function(){
	$(document).ajaxError(
	        function(event,xhr,options,exc ){
	            if(xhr.status == 'undefined'){
	                return;
	            }
	            var msg;
	            switch (xhr.status) {
	                case 400 :
	                    msg = '服务异常';
	                    break;
	                case 401 :
	                    msg = '身份认证异常';
	                    break;
	                case 403 :
	                    msg = "权限受限";
	                    break;
	                case 404 :
	                    msg = "资源不存在";
	                    break;
	                case 500 :
	                    msg = "运行异常";
	                    break;
	                default :
	                    msg = "未知服务异常";
	            }
	            if (xhr.responseJSON && xhr.responseJSON.message && xhr.responseJSON.message != "") {
	                msg = xhr.responseJSON.message;
	            }
	            alert(msg);
	        }
	);
}); 
</script>
</head>

