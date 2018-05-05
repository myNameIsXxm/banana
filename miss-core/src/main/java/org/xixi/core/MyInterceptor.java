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
	 * ҵ����������֮ǰ������ ����:ǰ�ó�ʼ������,Ȩ���ж�
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
	 * ��ҵ��������������DispatcherServlet��ͻ��˷�����Ӧǰ������ ����:��Controller
	 * ����֮���ModelAndView ������в���
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// logger.info("--postHandle--");
	}

	/**
	 * ��DispatcherServlet��ȫ����������󱻵��� ����:��Դ����
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// logger.info("--afterCompletion--");
	}
}
