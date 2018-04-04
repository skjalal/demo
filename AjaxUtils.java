package com.utils;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class AjaxUtils {
	private static String ajaxResponse = "text/html";
	private static String jsonResponse = "application/json";
	private static Logger log = Logger.getLogger(AjaxUtils.class);
	//https://datatables.net/extensions/fixedcolumns/examples/styling/bootstrap.html
	private AjaxUtils() {

	}

	public static void jsonResponse(final Object responseData) {
		log.debug("Enter into JsonResponse...!");
		response(responseData, jsonResponse);
	}
	
	public static void ajaxResponse(final Object responseData) {
		log.debug("Enter into AjaxResponse...!");
		response(responseData,ajaxResponse);
	}

	private static void response(final Object responseData, String responseType) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(responseType);
		response.setHeader("Cache-Control", "no-store");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			log.error(e);
		}
		out.println(responseData);
		out.flush();
	}
}
