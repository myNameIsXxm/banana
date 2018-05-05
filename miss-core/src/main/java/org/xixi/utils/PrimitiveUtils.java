package org.xixi.utils;

public final class PrimitiveUtils {

	public static int noNull(Integer val) {
		return noNull(val, 0);
	}

	public static double noNull(Double val) {
		return noNull(val, 0d);
	}

	public static float noNull(Float val) {
		return noNull(val, 0f);
	}

	public static boolean noNull(Boolean val) {
		return noNull(val, false);
	}

	public static int noNull(Integer val, int defaultVal) {
		if (val == null) {
			return defaultVal;
		}
		return val;
	}

	public static double noNull(Double val, double defaultVal) {
		if (val == null) {
			return defaultVal;
		}
		return val;
	}

	public static float noNull(Float val, float defaultVal) {
		if (val == null) {
			return defaultVal;
		}
		return val;
	}

	public static boolean noNull(Boolean val, boolean defalutVal) {
		if (val == null) {
			return defalutVal;
		}
		return val;
	}
}
