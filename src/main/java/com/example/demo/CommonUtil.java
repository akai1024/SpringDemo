package com.example.demo;

import java.util.Calendar;
import java.util.Date;

public class CommonUtil {

	/**
	 * 是否是空字串
	 */
	public static boolean isEmptyString(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * 是否是有效字串
	 */
	public static boolean isEffectiveString(String str) {
		return !isEmptyString(str);
	}

	public static String dateToSimpleString(Date date) {
		if (date == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(cal.get(Calendar.YEAR));
		sBuilder.append("/");
		sBuilder.append((cal.get(Calendar.MONTH) + 1));
		sBuilder.append("/");
		sBuilder.append(cal.get(Calendar.DATE));
		sBuilder.append("-");
		sBuilder.append(cal.get(Calendar.HOUR_OF_DAY));
		sBuilder.append(":");
		sBuilder.append(cal.get(Calendar.MINUTE));
		sBuilder.append(":");
		sBuilder.append(cal.get(Calendar.SECOND));
		return sBuilder.toString();
	}

}
