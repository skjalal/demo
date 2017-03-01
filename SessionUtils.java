package com.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class SessionUtils {
	private static HttpServletRequest request = ServletActionContext.getRequest();
	private static HttpServletResponse response = ServletActionContext.getResponse();
	private static HttpSession session = request.getSession();
	private static ValueStack valueStack = ActionContext.getContext().getValueStack();

	private SessionUtils() {

	}

	public static HttpServletRequest getRequest() {
		return request;
	}

	public static HttpServletResponse getResponse() {
		return response;
	}

	public static HttpSession getSession() {
		return session;
	}

	public static Object getRequestAttribute(String key) {
		return request.getAttribute(key);
	}

	public static void setRequestAttribute(String key, Object value) {
		request.setAttribute(key, value);
	}

	public static Object getAttribute(String key) {
		return session.getAttribute(key);
	}

	public static void setAttribute(String key, Object value) {
		session.setAttribute(key, value);
	}

	public static void removeAttribute(String key) {
		session.removeAttribute(key);
	}

	public static String getRealPath() {
		return session.getServletContext().getRealPath("/");
	}

	public static String getRequestParameter(String key) {
		String value = request.getParameter(key);
		if (StringUtils.isEmpty(value)) {
			value = (String) getRequestAttribute(key);
		}
		value = StringUtils.isEmpty(value) ? "" : value;
		return value;
	}

	public static String getServerName() {
		return request.getServerName();
	}

	public static String getIpAddress() {
		return request.getRemoteAddr();
	}

	public static String getContextPath() {
		return request.getContextPath();
	}

	public static ValueStack getValueStack() {
		return valueStack;
	}

	public static Object getValueStackAttribute(final String key) {
		return valueStack.findValue(key);
	}

	public static void setValueStackAttribute(final String key, final Object value) {
		valueStack.set(key, value);
	}

	public static String[] getRequestParameterValues(String key) {
		return request.getParameterValues(key);
	}
}