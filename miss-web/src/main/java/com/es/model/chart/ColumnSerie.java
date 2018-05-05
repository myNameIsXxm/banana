package com.es.model.chart;

import java.util.List;

public class ColumnSerie extends Serie {
	
	{
		super.setType("column");
	}
	
	private List<Object> data;

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "{name:\"" + super.getName() + "\",type:\"" + super.getType() + "\", data:" + data + "}";
	}
}
