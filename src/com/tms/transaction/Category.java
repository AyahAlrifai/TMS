package com.tms.transaction;

public class Category {

	private Integer id;
	private int dkey;
	private String value;
	private String iconPath;

	public Category(Integer id, Integer dkey, String value, String iconPath) {
		super();
		this.id = id;
		this.value = value;
		this.iconPath = iconPath;
		this.dkey = dkey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getDkey() {
		return dkey;
	}

	public void setDkey(int dkey) {
		this.dkey = dkey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	@Override
	public String toString() {
		return "Category id:" + id + "\tdkey:" + dkey + "\tvalue:" + value + "\ticonPath:" + iconPath;
	}
}
