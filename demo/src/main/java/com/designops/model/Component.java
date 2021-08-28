package com.designops.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Component {
	
	private String status;
	private int userId;
	
	@Length(max=80)
	@NotBlank(message="PageTitle is mandatory")
	private String pageTitle;
	private int tenantId;
	
	@Length(max=300)
	@NotBlank(message="Description is mandatory")
	private String pageDescription;
	private int artifactId;
	private int version;
	
	private List<String> taskId;
	
	private List<ComponentDetail> componentDetails;;
	
	private List<Comment> comments;
	
private String userName;
	
	private String artifactType;
	
	private String artifactCategory;
	
	
	private String tenantName;
	
	@JsonIgnore
	private String lastModifiedDate;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public String getPageDescription() {
		return pageDescription;
	}

	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}

	public int getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<String> getTaskId() {
		return taskId;
	}

	public void setTaskId(List<String> taskId) {
		this.taskId = taskId;
	}

	public List<ComponentDetail> getComponentDetails() {
		return componentDetails;
	}

	public void setComponentDetails(List<ComponentDetail> componentDetails) {
		this.componentDetails = componentDetails;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getArtifactType() {
		return artifactType;
	}

	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
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

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	

}
