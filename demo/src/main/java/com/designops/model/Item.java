package com.designops.model;

import org.hibernate.validator.constraints.Length;

public class Item {
	
	
	@Length(max=50)
	private String colourName;
	
	private String hexaValue;
	
	private String rgbValue;

	public String getColourName() {
		return colourName;
	}

	public void setColourName(String colourName) {
		this.colourName = colourName;
	}

	public String getHexaValue() {
		return hexaValue;
	}

	public void setHexaValue(String hexaValue) {
		this.hexaValue = hexaValue;
	}

	public String getRgbValue() {
		return rgbValue;
	}

	public void setRgbValue(String rgbValue) {
		this.rgbValue = rgbValue;
	}
	
	
	

}
