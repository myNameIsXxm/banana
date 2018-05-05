<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>
<%
//String user = "1485528984";
String user = "1010000001";
//金华市地方税务局开发区税务分局 --> 金华市地方税务局开发区税务分局办公室
%>
<h1>房屋交易</h1>
<a href="fwjyysyj/index.html?userId=<%=user%>" target="_blank">一手移交</a><br/>
<a href="fwjy/esyj.html?userId=<%=user%>" target="_blank">二手移交</a><br/>
<a href="fwjyessh/index.html?userId=<%=user%>" target="_blank">交易信息收回</a><br/>
</body>
</html>