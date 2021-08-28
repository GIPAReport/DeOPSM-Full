package com.designops.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="systemparameter")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemParameter {

	@Id
	@GeneratedValue
	private int paramid;
	
	private String paramkey;
	
	private String paramvalue;

	public int getParamid() {
		return paramid;
	}

	public void setParamid(int paramid) {
		this.paramid = paramid;
	}

	public String getParamkey() {
		return paramkey;
	}

	public void setParamkey(String paramkey) {
		this.paramkey = paramkey;
	}

	public String getParamvalue() {
		return paramvalue;
	}

	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}
	
	
	
	
}
