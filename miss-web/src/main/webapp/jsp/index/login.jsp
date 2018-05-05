<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
	<link href="css/login-jssh.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>js/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>js/jquery-easyui-1.4.5/themes/metro-blue/easyui.css">
</head>
<body>
	<div class="login">
		<div class="login_logo"></div>
		<div class="login_container">
			<div class="login_main">
				<div class="login_main_inner">
					<div class="login_top">
						<div class="login_top_right"></div>
					</div>
					<div class="login_form">
						<div class="form-group clear-float">
							<label>用户名：</label>
							<div class="input-text-box">
								<input type="text" id="j_username" name="j_username"
									value="kf_dx03" size="24" maxlength="24" onkeydown="keySearch(event)"/> <i
									class="ico ico-user" ></i>
								<div id="username-no-data" class="verify-tips">请输入用户名</div>
							</div>
						</div>
						<div class="form-group clear-float">
							<label>密 码：</label>
							<div class="input-text-box" id="login_pwd">
								<input type="password" id="j_password" name="j_password"
									value="" size="24" maxlength="24" onkeydown="keySearch(event)"/> <i
									class="ico ico-password"></i>
								<div id="password-no-data" class="verify-tips">请输入密码</div>
							</div>
						</div>
						<div class="button-group">
							<input type="button" id="btnLogin" onclick="valiadate()"
								name="Submit" value="登录" class="button-blue mr-50" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		window.alert=function(a){
			$.messager.alert({
				title:'提示信息',
				msg:a
			});
		}
		function keySearch(e){
			if(e.keyCode==13){
				valiadate();
			}
		}
		function valiadate() {
			$("#username-no-data").hide();
			$("#password-no-data").hide();
			var uo = document.getElementById('j_username');
			var po = document.getElementById('j_password');
			if (uo && !uo.value) {
				uo.focus();
				$("#username-no-data").show();
				return false;
			}
			if (po && !po.value) {
				po.focus();
				$("#password-no-data").show();
				return false;
			}
			$.ajax({
				url : "login/check",
				data : {
					username : uo.value,
					pass : po.value
				},
				type:"post",
				success : function(da) {
					if(da.hasOwnProperty("errMsg")){
						alert(da.errMsg);
						return false;
					}
					if (da == "success") {
						location.href = location.href.replace("welcome",
								"main");
					} else {
						alert(da);
					}
				}
			});
		}
	</script>
</body>
</html>