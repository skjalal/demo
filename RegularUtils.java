package com.utils;

import java.text.DecimalFormat;
import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class RegularUtils {
	private static final String DOT = ".";
	private static String decimal = "^(([\\d]+)?)(\\.?)(([\\d]+)?)$";
	private static DecimalFormat decimalformater = new DecimalFormat("#.##");

	private RegularUtils() {

	}

	public static boolean isEquals(Boolean isCaseSensitive, String str1, String str2) {
		String in = "";
		String out = "";
		if (NumberUtils.isNumber(str1)) {
			in = convertIntoDecimal(str1);
		}
		if (NumberUtils.isNumber(str2)) {
			out = convertIntoDecimal(str2);
		}
		return isCaseSensitive ? StringUtils.equals(in, out) : StringUtils.equalsIgnoreCase(in, out);
	}

	public static String convertIntoDecimal(String input) {
		if (StringUtils.isEmpty(input)) {
			return "0.00";
		} else if (input.equals(DOT) || !input.matches(decimal)) {
			return input;
		}
		String beforeDot;
		String afterDot = "00";
		String output;
		if (StringUtils.contains(input, DOT)) {
			beforeDot = StringUtils.substringBefore(input, DOT);
			afterDot = StringUtils.substringAfter(input, DOT);
			beforeDot = StringUtils.isEmpty(beforeDot) ? "0" : beforeDot;
			if (afterDot.length() == 1) {
				afterDot = afterDot + "0";
			} else if (afterDot.length() >= 2) {
				if (!("00").equalsIgnoreCase(afterDot)) {
					double number = Double.parseDouble(input);
					return decimalformater.format(number);
				}
				afterDot = StringUtils.substring(afterDot, 0, 2);
			}
			afterDot = StringUtils.isEmpty(afterDot) ? "00" : afterDot;
			output = beforeDot + DOT + afterDot;
		} else {
			output = input + DOT + afterDot;
		}
		return output;
	}
}