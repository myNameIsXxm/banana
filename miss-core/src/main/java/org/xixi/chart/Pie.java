package org.xixi.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Pie extends Chart {

	public Pie() {

	}

	public Pie(Chart chart) {
		this.setTitle(chart.getTitle());
		this.setSubtitle(chart.getSubtitle());
		this.setYtitle(chart.getYtitle());
	}

	public static List<Serie> initSeries(List<String> cate,
			List<TreeMap<String, Object>> list) {
		List<Serie> series = new ArrayList<Serie>();
		for (Map<String, Object> m : list) {
			PieSerie serie = new PieSerie();
			serie.setName(m.get("NAME").toString());
			List<List<Object>> data = new ArrayList<List<Object>>();
			for (String s : cate) {
				List<Object> arr = new ArrayList<Object>();
				arr.add("\""+s+"\"");
				arr.add(m.get(s));
				data.add(arr);
			}
			serie.setData(data);
			series.add(serie);
		}
		return series;
	}
}
