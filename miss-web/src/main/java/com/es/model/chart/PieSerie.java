package com.es.model.chart;

import java.util.List;

public class PieSerie extends Serie {
	
	{
		super.setType("pie");
	}
	
	private List<List<Object>> data;

	public List<List<Object>> getData() {
		return data;
	}

	public void setData(List<List<Object>> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "{name:\"" + super.getName() + "\",type:\"" + super.getType() + "\", data:" + data + "}";
	}
}
