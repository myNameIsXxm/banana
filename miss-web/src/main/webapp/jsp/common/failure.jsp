<%@ page language="java" contentType="text/html; charset=GBK"  
    pageEncoding="GBK"%>  
<%@ page import="java.lang.Exception"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">  
<title>错误页面</title>  
</head>  
<body>  
<h1>出错了该页面还没制作完成,请联系管理员更新系统</h1>  
<%  
Exception e = (Exception)request.getAttribute("exception");  
out.print(e.getMessage());  
%>  
</body>  
</html>  