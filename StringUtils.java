package com.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
	private static final boolean CASE_SENSITIVE = true;
	private static final boolean NON_CASE_SENSITIVE = false;

	public static Map<Integer, String> toMap(String... records) {
		if (records == null)
			return null;
		Map<Integer, String> result = new LinkedHashMap<>(records.length);
		int index = 0;
		for (String record : records) {
			result.put(index++, record);
		}
		return result;
	}

	public static boolean isEquals(String inputString, String... target) {
		return checkEquallOrNot(CASE_SENSITIVE, inputString, target);
	}

	public static boolean equalsIgnoreCase(String inputString, String... target) {
		return checkEquallOrNot(NON_CASE_SENSITIVE, inputString, target);
	}

	private static boolean checkEquallOrNot(boolean caseSensitive, String inputString, String... targets) {
		if (StringUtils.isEmpty(inputString)) {
			return false;
		}

		for (String target : targets) {
			if (!RegularUtils.isEquals(caseSensitive, inputString, target)) {
				return false;
			}
		}
		return true;
	}

	public static List<String> toList(String... records) {
		List<String> result = new ArrayList<>(records.length);
		int index = 0;
		for (String record : records) {
			result.add(index++, record);
		}
		return result;
	}
}