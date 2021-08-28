//package com.designops.model;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import org.hibernate.envers.Audited;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//@Data
//@Document(indexName = "csmarketplace")
//@NoArgsConstructor
//@AllArgsConstructor
//public class CMS {
//	
//	@Id
//	@GeneratedValue
//	private String id;
//	
//	@Field(type=FieldType.Text,name="pageDescription")
//	private String pageDescription;
//	
//	@Field(type=FieldType.Text,name="pageTitle")
//	private String pageTitle;
//	
//	
//	@Field(type=FieldType.Text,name="artifactType")
//	private String artifactType;
//	
//	@Field(type=FieldType.Text,name="taskId")
//	private String taskId;
//	
//	@Field(type=FieldType.Text,name="userName")
//	private String userName;
//	
//	@Field(type=FieldType.Text,name="artifactId")
//	private String artifactId;
//	
//	@Field(type=FieldType.Text,name="tenantName")
//	private String tenantName;
//	
//	@Field(type=FieldType.Text,name="tenantId")
//	private String tenantId;
//	
//	@Field(type=FieldType.Text,name="artifactCategory")
//	private String artifactCategory;
//	
//	@Field(type=FieldType.Text,name="status")
//	private String status;
//	
//	
//	@Field(type=FieldType.Text,name="lastModifiedDate")
//	private String lastModifiedDate;
//
//
//
//
//
//	public String getId() {
//		return id;
//	}
//
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//
//	public String getPageDescription() {
//		return pageDescription;
//	}
//
//
//	public void setPageDescription(String pageDescription) {
//		this.pageDescription = pageDescription;
//	}
//
//
//	public String getPageTitle() {
//		return pageTitle;
//	}
//
//
//	public void setPageTitle(String pageTitle) {
//		this.pageTitle = pageTitle;
//	}
//
//
//	public String getArtifactType() {
//		return artifactType;
//	}
//
//
//	public void setArtifactType(String artifactType) {
//		this.artifactType = artifactType;
//	}
//
//
//	public String getTaskId() {
//		return taskId;
//	}
//
//
//	public void setTaskId(String taskId) {
//		this.taskId = taskId;
//	}
//
//
//	public String getUserName() {
//		return userName;
//	}
//
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//
//	public String getArtifactId() {
//		return artifactId;
//	}
//
//
//	public void setArtifactId(String artifactId) {
//		this.artifactId = artifactId;
//	}
//
//
//	public String getTenantName() {
//		return tenantName;
//	}
//
//
//	public void setTenantName(String tenantName) {
//		this.tenantName = tenantName;
//	}
//
//
//	public String getTenantId() {
//		return tenantId;
//	}
//
//
//	public void setTenantId(String tenantId) {
//		this.tenantId = tenantId;
//	}
//
//
//	public String getArtifactCategory() {
//		return artifactCategory;
//	}
//
//
//	public void setArtifactCategory(String artifactCategory) {
//		this.artifactCategory = artifactCategory;
//	}
//
//
//	public String getStatus() {
//		return status;
//	}
//
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//
//	public String getLastModifiedDate() {
//		return lastModifiedDate;
//	}
//
//
//	public void setLastModifiedDate(String lastModifiedDate) {
//		this.lastModifiedDate = lastModifiedDate;
//	}
//
//	
//	
//
//
//}
