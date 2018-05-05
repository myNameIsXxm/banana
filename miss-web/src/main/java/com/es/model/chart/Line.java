package com.es.model.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Line extends Chart {
	private List<String> categories;
	public Line(){
		
	}
	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		for (int i = 0; i < categories.size(); i++) {
			if (!categories.get(i).startsWith("'")) {
				categories.set(i, "'" + categories.get(i) + "'");
			}
		}
		this.categories = categories;
	}
	public Line(Chart chart){
		this.setTitle(chart.getTitle());
		this.setSubtitle(chart.getSubtitle());
		this.setYtitle(chart.getYtitle());
	}
	
	public static List<Serie> initSeries(List<String> cate,
			List<TreeMap<String, Object>> list) {
		List<Serie> series = new ArrayList<Serie>();
		for (Map<String, Object> m : list) {
			LineSerie serie = new LineSerie();
			serie.setName(m.get("NAME").toString());
			List<Object> data = new ArrayList<Object>();
			for (String s : cate) {
				data.add(m.get(s));
			}
			serie.setData(data);
			series.add(serie);
		}
		return series;
	}
}
