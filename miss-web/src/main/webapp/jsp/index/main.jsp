<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.es.model.User"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>房屋交易数据移交</title>
<%@ include file="/WEB-INF/pages/include/head.jsp"%>
<style>
.linkdiv{
	height: 20px;
    margin-top: 5px;
    margin-bottom: 5px;
    text-align: left;
    padding-left: 10px;
    cursor:pointer;
    border-bottom: 1.5px solid #e0e0e0;
}
.linkdiv:hover{
	background-color:#dfe8f6;
}
.titleimg{
	width:12px;height:12px;
}
.titlespan{
	font-size:12px;
	color:#000080;
}
</style>
<!-- <link href="css/index.css" type="text/css" rel="stylesheet" />
<link href="css/main.css" type="text/css" rel="stylesheet" />
<link href="css/Guide.css" type="text/css" rel="stylesheet" /> -->
</head>
<body class="easyui-layout">
	<c:if test="${showTitle}">
		<div data-options="region:'north',border:false" style="margin:5px;">
			<span style="font-size: 30px;color: #0096ff;">房产交易完税数据交换平台</span>
			<span id="time_X" style="font-size: 16px;padding-left: 15px;color: #a0a0a0;"></span>
		</div>
	</c:if>
	<div data-options="region:'west'" style="width:150px;overflow: hidden;" title="欢迎[${user.name}]">
		
		<div class="linkdiv" onclick="openNewWin('一手房完税数据移交','fwjyysyj/index.html')">
			<img alt="" src="<%=request.getContextPath()%>/images/1.png" class="titleimg"/>
			<span class="titlespan">一手房完税数据移交</span>
		</div>
		<div class="linkdiv" onclick="openNewWin('二手房完税数据移交','fwjy/esyj.html')">
			<img alt="" src="<%=request.getContextPath()%>/images/2.png" class="titleimg"/>
			<span class="titlespan">二手房完税数据移交</span>
		</div>
		<div class="linkdiv" onclick="openNewWin('完税信息收回','fwjyessh/index.html')">
			<img alt="" src="<%=request.getContextPath()%>/images/3.png" class="titleimg"/>
			<span class="titlespan">完税信息收回</span>
		</div>
		<div class="linkdiv" onclick="openNewWin('一手房房管信息查询','fgtjysf.html')">
			<img alt="" src="<%=request.getContextPath()%>/images/4.png" class="titleimg"/>
			<span class="titlespan">一手房房管信息查询</span>
		</div>
		<div class="linkdiv" onclick="openNewWin('二手房房管信息查询','fgtjesf.html')">
			<img alt="" src="<%=request.getContextPath()%>/images/4.png" class="titleimg"/>
			<span class="titlespan">二手房房管信息查询</span>
		</div>
		<div class="linkdiv" onclick="openNewWin('地税信息接收与推送汇总表','hz.html')">
			<img alt="" src="<%=request.getContextPath()%>/images/4.png" class="titleimg"/>
			<span class="titlespan">信息接收与推送汇总</span>
		</div> 
		<c:if test="${showIndex}">
		<div class="linkdiv" onclick="openNewWin('业务数据统计','fwjysj.html')">
			<img alt="" src="<%=request.getContextPath()%>/images/4.png" class="titleimg"/>
			<span class="titlespan">业务数据统计</span>
		</div>
		</c:if>
		<div class="linkdiv" onclick="openNewWin('特殊移交','bj.html')">
			<img alt="" src="<%=request.getContextPath()%>/images/4.png" class="titleimg"/>
			<span class="titlespan">特殊移交</span>
		</div>
		<%-- <div class="linkdiv" onclick="openNewWin('网络测试','wlcs.html')">
			<img alt="" src="<%=request.getContextPath()%>/images/4.png" class="titleimg"/>
			<span class="titlespan">网络测试</span>
		</div> --%>
	</div>
	<div data-options="region:'center'" >
		<div id="main_right" class="easyui-tabs" pill="true" tabPosition="top"
			style="height: 100%;width: 100%;">
		</div>
	</div>
	<script type="text/javascript">
	
		Date.prototype.Format = function(fmt){ 
			  var o = {   
			    "M+" : this.getMonth()+1,                 //月份   
			    "d+" : this.getDate(),                    //日   
			    "h+" : this.getHours(),                   //小时   
			    "m+" : this.getMinutes(),                 //分   
			    "s+" : this.getSeconds(),                 //秒   
			    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
			    "S"  : this.getMilliseconds()             //毫秒   
			  };   
			  if(/(y+)/.test(fmt))   
			    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
			  for(var k in o)   
			    if(new RegExp("("+ k +")").test(fmt))   
			  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
			  return fmt;   
		}	
	
		$(document).ready(function(){
			if(${showIndex}){
				openNewWin('首页','index.html');
			}
			timeChange();
		});
		function timeChange(){
			$("#time_X").html(new Date().Format("yyyy-MM-dd hh:mm:ss"));
			setTimeout(timeChange,1000);
		}
		function openNewWin(title,src){
			if($("#main_right").tabs("exists",title)){
				$("#main_right").tabs("select",title);
			}else{
				$("#main_right").tabs("add",{
					title:title,
					closeable:true,
					content:"<iframe src='"+rootPath+"/"+src+"' style='width:100%;height:98%'></iframe>"
				});
			}
		}
		function exit(){
			confirm("确认注销登录",function(){
				$.ajax({
					url:rootPath+"/login/exit",
					type:"post",
					success:function(da){
						if(da.hasOwnProperty("errMsg")){
							closeProgress();
							alert(da.errMsg);
							return false;
						}
						location.reload(true);
					}
					
				});				
			});
		}
	</script>
</body>
</html>
