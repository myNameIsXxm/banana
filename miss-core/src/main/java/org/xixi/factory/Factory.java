package org.xixi.factory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xixi.enums.Fields;
import org.xixi.exception.AuthorizationException;
import org.xixi.model.User;

public class Factory {

	private static List<String> excludedUrl; // 非登录即可访问的URL，采用单例，对所有用户都是一个实例

	/**
	 * User ， 每个session一个实例
	 */
	public static User getUser() throws AuthorizationException {
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();
		Object obj = session.getAttribute(Fields.F_USER);
		if (obj == null) {
			throw new AuthorizationException();
		}
		return (User) obj;
	}
	
	public static Long getUserId() throws AuthorizationException {
		return getUser().getId();
	}
	
	/*public static String pass(String pass) throws NoSuchAlgorithmException {
		return MD5Util.md5Of32Upper(pass);
	}*/

	public static void setUser(User user) {
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();
		session.setAttribute(Fields.F_USER, user);
		session.setMaxInactiveInterval(-1);
	}

	public static void clearUser() {
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();
		session.removeAttribute(Fields.F_USER);
	}

	public static List<String> getExcludedUrl() {
		if (excludedUrl == null) {
			synchronized (Factory.class) {
				if (excludedUrl == null) {
					excludedUrl = new ArrayList<String>();
					excludedUrl.add("/");
					excludedUrl.add("/error");
					excludedUrl.add("/nologin.html");
					excludedUrl.add("login/check");
					excludedUrl.add("login.jsp");
					excludedUrl.add("main.html");
					excludedUrl.add("post/title");
					excludedUrl.add("post/username");
					excludedUrl.add("login.html");
					excludedUrl.add("newsShow.html");
				}
			}
		}
		return excludedUrl;
	}
	
	public static String getUUID() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).toString()+RandomUtils.nextInt(1000,9999);
	}

}
