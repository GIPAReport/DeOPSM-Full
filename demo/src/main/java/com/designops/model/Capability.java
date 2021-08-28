package com.designops.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection="Capabilities")
public class Capability {
	
	private int artifactId;
	private int version;
	private String status;
	private String artifactCategory;
	private int userId;
	
	private List<CapabilityDetail> capabilityDetails;
	
	private String tenantName;
	private int tenantId;
	private String userName;
	private String artifactType;

	private List<String> taskId;
	private List<Comment> comments;
	
	@JsonIgnore
	private String lastModifiedDate;
	
	@Length(max=80)
	@NotBlank(message="Page title is mandatory")
	private String pageTitle;
	
	@Length(max=300)
	@NotBlank(message="Description is mandatory")
	private String pageDescription;
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArtifactCategory() {
		return artifactCategory;
	}

	public void setArtifactCategory(String artifactCategory) {
		this.artifactCategory = artifactCategory;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<CapabilityDetail> getCapabilityDetails() {
		return capabilityDetails;
	}

	public void setCapabilityDetails(List<CapabilityDetail> capabilityDetails) {
		this.capabilityDetails = capabilityDetails;
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

	public List<String> getTaskId() {
		return taskId;
	}

	public void setTaskId(List<String> taskId) {
		this.taskId = taskId;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getPageDescription() {
		return pageDescription;
	}

	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}

	
	
}
