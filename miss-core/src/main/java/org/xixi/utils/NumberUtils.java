package org.xixi.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NumberUtils {

	public static String r2Str(Object v, int n) {
		return String.format("% ,." + n + "f", v);
	}

	public static String toStr(double a) {
		String s = String.valueOf(a);
		if (s.indexOf("E") != -1) {
			s = s.substring(0, s.indexOf("E") + 1) + "+" + s.substring(s.indexOf("E") + 1);
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println(toStr(Double.parseDouble("1.2E11") + 10000));
	}

	public static double round(double v, int scale) {
		if (v == 0)
			return 0;
		if (scale < 0) {
			throw new IllegalArgumentException("scale must be a possitive int.");
		}

		BigDecimal b = new BigDecimal(Double.toString(v));

		BigDecimal one = new BigDecimal("1");

		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double[] round(double[] arr, int scale) {
		if (arr == null)
			return new double[0];
		for (int i = 0; i < arr.length; ++i)
			arr[i] = round(arr[i], scale);
		return arr;
	}

	public static Double[] demolition(List<Double> vals, double coe) {
		List<Double> ret = new ArrayList<Double>();

		for (Double v : vals) {
			if (v == null) {
				ret.add(0D);
			} else {
				ret.add(NumberUtils.round(v * coe, 2));
			}
		}

		return ret.toArray(new Double[] {});
	}
}
