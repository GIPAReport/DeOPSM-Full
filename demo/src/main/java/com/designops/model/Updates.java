package com.designops.model;

import org.hibernate.validator.constraints.Length;

public class Updates {

	private String dateOfUpdate;
	
	private String version;
	
	@Length(max=800)
	private String updateDescription;

	public String getDateOfUpdate() {
		return dateOfUpdate;
	}

	public void setDateOfUpdate(String dateOfUpdate) {
		this.dateOfUpdate = dateOfUpdate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUpdateDescription() {
		return updateDescription;
	}

	public void setUpdateDescription(String updateDescription) {
		this.updateDescription = updateDescription;
	}
	
	
	
	
}
