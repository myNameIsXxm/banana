package com.es.controller.base;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.es.exception.AuthorizationException;
import com.es.factory.Factory;
import com.es.model.User;
import com.es.service.face.BaseServiceFace;

@Controller
public class MainController {
	
	@Inject
	private BaseServiceFace baseService;

	/**
	 * 默认首页
	 */
	@RequestMapping("/")
	public ModelAndView init(HttpServletResponse response, HttpServletRequest result) {
		return new ModelAndView("redirect:/main.html?d=" + new Date().getTime());
	}
	
	@RequestMapping("/error")
	public String error() {
		return "error";
	}

	/**
	 * 实际首页
	 * 
	 * @throws AuthorizationException
	 */
	@RequestMapping(value = "/main.html")
	public ModelAndView main(HttpServletResponse response, HttpServletRequest result, String menuList)
			throws AuthorizationException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", Factory.getUser());
		return new ModelAndView("index/main", map);
	}

	/**
	 * 登录页
	 */
	@RequestMapping(value = "/welcome.html")
	public String welcome() {
		return "index/login";
	}

	/**
	 * 不用登录的登录页
	 * @throws Exception 
	 */
	@RequestMapping(value = "/nologin.html")
	@ResponseBody
	public ModelAndView nologin(String userId) throws Exception {
		List<Map<String, Object>> list = baseService
				.queryToMapForSql("select SUID, FULLNAME from XT_SYSUSERS t WHERE t.SUID=?", userId);
		if (list != null && list.size() == 1) {
			Map<String, Object> map = list.get(0);
			Factory.setUser(new User(Long.parseLong(map.get("SUID").toString()), map.get("FULLNAME").toString()));
		} else {
			Factory.setUser(new User(Long.parseLong(userId), "链接用户"));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", Factory.getUser());
		return new ModelAndView("index/main", map);
	}

}
