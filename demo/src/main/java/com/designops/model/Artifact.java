package com.designops.model;

import java.sql.Date;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.envers.Audited;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Artifact")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Audited
public class Artifact {
	
	@Id
	@GeneratedValue
	private int artifactId;
	
	@NotBlank(message="Title is mandatory")
	private String artifactTitle;
	
	private String artifactDescription;
	
	@NotNull
	private int tenantId;
	
	private String tenantName;
	
	private String taskId;
	
	private String artifactType;
	
	private int userId;
	
	private String artifactCategory;
	
	@NotBlank(message="User Name is mandatory")
	private String createdBy;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime lastModifiedOn;
	
private String status;
	private boolean isActive;
	private String modifiedBy;
	
	public int getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}
	public String getArtifactTitle() {
		return artifactTitle;
	}
	public void setArtifactTitle(String artifactTitle) {
		this.artifactTitle = artifactTitle;
	}
	public String getArtifactDescription() {
		return artifactDescription;
	}
	public void setArtifactDescription(String artifactDescription) {
		this.artifactDescription = artifactDescription;
	}
	public int getTenantId() {
		return tenantId;
	}
	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getArtifactType() {
		return artifactType;
	}
	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getArtifactCategory() {
		return artifactCategory;
	}
	public void setArtifactCategory(String artifactCategory) {
		this.artifactCategory = artifactCategory;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public LocalDateTime getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(LocalDateTime lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	
	
	
	
	

}
