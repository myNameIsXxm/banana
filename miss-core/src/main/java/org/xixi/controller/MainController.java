package org.xixi.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xixi.exception.AuthorizationException;
import org.xixi.factory.Factory;
import org.xixi.model.User;
import org.xixi.service.face.BaseServiceFace;

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

}
