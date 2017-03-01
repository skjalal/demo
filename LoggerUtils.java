package com.utils;

public class LoggerUtils {
	private static String startMessage = "Enter into  %s in %s";
	private static String endMessage = "Ending of %s in %s";

	private LoggerUtils() {

	}

	public static String methodStartingMessage() {
		return getMessage(startMessage);
	}

	public static String methodEndingMessage() {
		return getMessage(endMessage);
	}

	private static String getMessage(String message) {
		StackTraceElement stackElement = Thread.currentThread().getStackTrace()[3];
		String methodName = stackElement.getMethodName();
		String className = stackElement.getClassName();
		return String.format(message, methodName, className);
	}
}