<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>用户错误</title>
<style type="text/css">
td {
	border: 1px solid black;
}

table {
	border-collapse: collapse;
}
</style>
</head>
<body>
	<h1>获取用户异常</h1>
	<h3>调试信息</h3>
	<table>
		<tr>
			<td width="50%">UserID</td>
			<td>${userId}</td>
		</tr>
		<tr>
			<td colspan="2">获取到的结果为空</td>
		</tr>
	</table>
</body>
</html>