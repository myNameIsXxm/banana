package org.xixi.chart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Column extends Chart {
	
	private List<String> categories;
	private Double average;

	public Column() {

	}

	public void initAverage() {
		Double average = 0d;
		int number = 0;
		for (Serie s : super.getSeries()) {
			ColumnSerie ss = (ColumnSerie)s;
			for (Object o : ss.getData()) {
				average += Double.parseDouble(o.toString());
				number++;
			}
		}
		this.average = new BigDecimal(average / number).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
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

	public Column(Chart chart) {
		this.setTitle(chart.getTitle());
		this.setSubtitle(chart.getSubtitle());
		this.setYtitle(chart.getYtitle());
	}
	
	public static List<ColumnSerie> initSeries(List<String> cate, List<TreeMap<String, Object>> list) {
		List<ColumnSerie> series = new ArrayList<ColumnSerie>();
		for (Map<String, Object> m : list) {
			ColumnSerie serie = new ColumnSerie();
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

	public static List<Serie> initSeries(List<String> cate, List<TreeMap<String, Object>> list, String type) {
		List<Serie> series = new ArrayList<Serie>();
		for (Map<String, Object> m : list) {
			ColumnSerie serie = new ColumnSerie();
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
