package org.xixi.chart;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class Chart {
	private String title;
	private String subtitle;
	private String ytitle;
	private List<Serie> series;

	public Chart(){}
	public Chart(String title, String subtitle, String ytitle) {
		this.title = title;
		this.subtitle = subtitle;
		this.ytitle = ytitle;
	}
	
	public List<Serie> getSeries() {
		return series;
	}

	public void setSeries(List<Serie> series) {
		this.series = series;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getYtitle() {
		return ytitle;
	}

	public void setYtitle(String ytitle) {
		this.ytitle = ytitle;
	}

	public static List<String> initCategories(List<TreeMap<String, Object>> l1) {
		Set<String> set = l1.get(0).keySet();
		List<String> categories = new ArrayList<String>();
		for (String m : set) {
			if (!m.equals("NAME")) {
				categories.add(m);
			}
		}
		return categories;
	}
}
