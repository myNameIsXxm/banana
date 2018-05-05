package org.xixi.model;

import org.apache.commons.lang3.StringUtils;
import org.xixi.enums.Format;

public class HeadModel {
	private String name;
	private String colname;
	private Integer colspan = 1; // 所占列数
	private Integer rowspan = 1; // 所占行数
	private Integer inlineNum = 1; // 在表头第几行
	private Integer sortNum = 1; // 表头排序号，父表头的序号为子表头排序的最大值
	private String align = "left";
	private String linkUrl;
	private Integer width;
	private Boolean ischeck = false;
	private String format;
	public HeadModel(String name, String colname, Integer sortNum, Integer width) {
		this.name = name;
		this.sortNum = sortNum;
		this.colname = StringUtils.upperCase(colname);
		this.width = width;
	}
	
	public HeadModel(String name, String colname, Integer sortNum, Integer width,Format format) {
		this.name = name;
		this.sortNum = sortNum;
		this.colname = StringUtils.upperCase(colname);
		this.width = width;
		this.format=format.toString();
	}

	public HeadModel(String name, String colname, Integer colspan, Integer rowspan, Integer inlineNum, Integer sortNum,
			Integer width) {
		this.name = name;
		this.sortNum = sortNum;
		this.colname = StringUtils.upperCase(colname);
		this.colspan = colspan;
		this.rowspan = rowspan;
		this.inlineNum = inlineNum;
		this.width = width;
	}
	
	public HeadModel(String name, String colname, Integer colspan, Integer rowspan, Integer inlineNum, Integer sortNum,
			Integer width,Format format) {
		this.name = name;
		this.sortNum = sortNum;
		this.colname = StringUtils.upperCase(colname);
		this.colspan = colspan;
		this.rowspan = rowspan;
		this.inlineNum = inlineNum;
		this.width = width;
		this.format=format.toString();
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getColspan() {
		return colspan;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	public Integer getRowspan() {
		return rowspan;
	}

	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}

	public Integer getInlineNum() {
		return inlineNum;
	}

	public void setInlineNum(Integer inlineNum) {
		this.inlineNum = inlineNum;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getColname() {
		return colname;
	}

	public void setColname(String colname) {
		this.colname = StringUtils.upperCase(colname);
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Boolean getIscheck() {
		return ischeck;
	}

	public void setIscheck(Boolean ischeck) {
		this.ischeck = ischeck;
	}

	public HeadModel addLinkUrl(String linkUrl) {
		setLinkUrl(linkUrl);
		return this;
	}

	public HeadModel addAlign(String align) {
		setAlign(align);
		return this;
	}

	public String getFormat() {
		if(StringUtils.isEmpty(format)){
			return Format.normal.toString();
		}
		return format;
	}

	public void setFormat(Format format) {
		this.format = format.toString();
	}
	
	public HeadModel addFormat(Format format){
		setFormat(format);
		return this;
	}

}
