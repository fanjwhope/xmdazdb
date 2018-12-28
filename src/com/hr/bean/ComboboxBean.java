package com.hr.bean;

public class ComboboxBean {
	private String value;
	private String text;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "ComboboxBean [value=" + value + ", text=" + text + "]";
	}
}
