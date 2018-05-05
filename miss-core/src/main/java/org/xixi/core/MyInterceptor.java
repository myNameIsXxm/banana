package org.xixi.core;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.xixi.enums.Fields;
import org.xixi.exception.AuthorizationException;
import org.xixi.factory.Factory;

public class MyInterceptor extends HandlerInterceptorAdapter implements Fields {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 业务处理器处理之前被调用 作用:前置初始化操作,权限判断
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException, AuthorizationException {
		String requestUri = request.getRequestURI();
		logger.info("requestUri : "+requestUri);
		List<String> urls = Factory.getExcludedUrl();
		for (String url : urls) {
			if (requestUri.endsWith(url)) {
				return true;
			}
		}
		if (Factory.getUser() == null) {
			throw new AuthorizationException(); 
		}
		return true;
	}

	/**
	 * 在业务处理完成请求后，在DispatcherServlet向客户端返回响应前被调用 作用:对Controller
	 * 处理之后的ModelAndView 对象进行操作
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// logger.info("--postHandle--");
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用 作用:资源清理
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// logger.info("--afterCompletion--");
	}
}
