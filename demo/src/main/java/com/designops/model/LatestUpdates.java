package com.designops.model;

import java.util.List;

public class LatestUpdates {
	
	private String pageTitle;
	
	private String artifactCategory;
	
	private String tenantName;
	
	private int tenantId;
	
	private int artifactId;
	
	private String artifactType;
	
	private List<UpdatesDTO> updatesDTO;

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getArtifactCategory() {
		return artifactCategory;
	}

	public void setArtifactCategory(String artifactCategory) {
		this.artifactCategory = artifactCategory;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public int getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}

	public String getArtifactType() {
		return artifactType;
	}

	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}

	public List<UpdatesDTO> getUpdatesDTO() {
		return updatesDTO;
	}

	public void setUpdatesDTO(List<UpdatesDTO> updatesDTO) {
		this.updatesDTO = updatesDTO;
	}

}
