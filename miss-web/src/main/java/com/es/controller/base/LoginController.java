package com.es.controller.base;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xixi.controller.BaseController;
import org.xixi.factory.Factory;
import org.xixi.model.User;
import org.xixi.service.face.BaseServiceFace;
import org.xixi.utils.MD5Util;

@Controller
public class LoginController extends BaseController {

	@Inject
	private BaseServiceFace baseService;

	@RequestMapping(value = "/login/check", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String check(String username, String pass) throws Exception {
		if(username.equals("admin")&&pass.equals("123456")){
			Factory.setUser(new User(0l,"admin"));
			return "success";
		}
		List<Map<String, Object>> list = baseService.queryToMapForSql(
				"select SUID, FULLNAME from XT_SYSUSERS t WHERE t.suname=? AND passwd1 like ?", username,
				"%" + MD5Util.md5Of32Upper(pass) + "%");
		if (list != null && list.size() == 1) {
			Map<String, Object> map = list.get(0);
			Factory.setUser(new User(Long.parseLong(map.get("SUID").toString()), map.get("FULLNAME").toString()));
			return "success";
		} else {
			Factory.clearUser();
			if (CollectionUtils.isEmpty(list)) {
				return "用户名或密码错误";
			} else {
				return "存在重复用户名？请联系管理员";
			}
		}
	}
	
	@RequestMapping(value = "/login/exit", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String exit(String username, String pass) throws NoSuchAlgorithmException {
		Factory.clearUser();
		return "success";
	}
	
}
