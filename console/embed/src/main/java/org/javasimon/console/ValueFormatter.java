package org.javasimon.console;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Base helper class to format values of attributes of simons. This class is
 * overriden to export differently JSON, CSV formats
 *
 * @author gquintana
 */
public class ValueFormatter {
	/**
	 * Formatter for times (durations)
	 */
	private TimeFormatType timeFormat = TimeFormatType.MILLISECOND;
	/**
	 * Formatter for timestamps
	 */
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Formatter for timestamps
	 */
	public DateFormat getDateFormat() {
		return dateFormat;
	}

	/**
	 * Formatter for timestamps
	 */
	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * Formatter for times (durations)
	 */
	public TimeFormatType getTimeFormat() {
		return timeFormat;
	}

	/**
	 * Formatter for times (durations)
	 */
	public void setTimeFormat(TimeFormatType timeFormat) {
		this.timeFormat = timeFormat;
	}
	/**
	 * Formats a null value
	 */
	public String formatNull() {
		return "";
	}
	/**
	 * Formats a string (name, note)
	 */
	public String formatString(String s) {
		return s == null ? formatNull() : s;
	}
	/**
	 * Formats a enumeration (type, state)
	 */
	public <T extends Enum<T>> String formatEnum(T t) {
		return formatString(t == null ? null : t.name());
	}
	/**
	 * Formats a number (counter,active)
	 */
	protected String formatNumber(String s) {
		return s == null ? formatNull() : s;
	}

	/**
	 * Formats a number (counter,active)
	 */
	public String formatNumber(Number n) {
		return formatNumber(isValidNumber(n) ? n.toString():null);
	}
	/**
	 * Test whether number is not a particular value (Min, Max, Infinity, NaN)
	 */
	private boolean isValidNumber(Number n) {
		if (n == null) {
			return false;
		} else if (n instanceof Integer) {
			Integer i = (Integer) n;
			return !(i == Integer.MIN_VALUE || i == Integer.MAX_VALUE);
		} else if (n instanceof Long) {
			Long l = (Long) n;
			return !(l == Long.MIN_VALUE || l == Long.MAX_VALUE);
		} else if (n instanceof Float) {
			Float f = (Float) n;
			return !(f == Float.MIN_VALUE || f == Float.MAX_VALUE 
				|| f == Float.NEGATIVE_INFINITY || f == Float.POSITIVE_INFINITY 
				|| f == Float.NaN);
		} else if (n instanceof Double) {
			Double d = (Double) n;
			return !(d == Double.MIN_VALUE || d == Double.MAX_VALUE 
				|| d == Double.NEGATIVE_INFINITY || d == Double.POSITIVE_INFINITY 
				|| d == Double.NaN);
		} else {
			return false;
		}
	}
	/**
	 * Formats a time (stopwatch values)
	 */
	private String postFormatTime(String s) {
		if (timeFormat == TimeFormatType.PRESENT) {
			return formatString(s);
		} else {
			return formatNumber(s);
		}
	}

	/**
	 * Formats a time (stopwatch values)
	 */
	public String formatTime(Long l) {
		String s = (isValidNumber(l) && l >=0L) ? timeFormat.format(l):null;
		return postFormatTime(s);
	}

	/**
	 * Formats a time (stopwatch values)
	 */
	public String formatTime(Double l) {
		String s = (isValidNumber(l) && l >=0D)? timeFormat.format(l):null;
		return postFormatTime(s);
	}
	/**
	 * Formats a timestamp (first/last usage, min/max timestamps)
	 */
	public String formatDate(Date d) {
		return formatString(d == null ? null : dateFormat.format(d));
	}

	/**
	 * Formats a timestamp (first/last usage, min/max timestamps)
	 */
	public String formatDate(Long l) {
		return formatDate((l == null || l <= 0L) ? null : new Date(l));
	}

	/**
	 * Formats anything: default formatter which doesn't do anything 
	 */
	public String formatObject(Object o) {
		if (o == null) {
			return formatNull();
		} else if (o instanceof String) {
			return (String) o;
		} else {
			return o.toString();
		}
	}
}
