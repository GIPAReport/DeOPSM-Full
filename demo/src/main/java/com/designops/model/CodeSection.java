package com.designops.model;

import org.hibernate.validator.constraints.Length;

public class CodeSection {
	
	
	private String syntax;
	
	@Length(max=5000)
	private String code;

	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
	

}
