package com.designops.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="RecentlyViewed")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecentlyViewed {

	@Id
	@GeneratedValue
	public Integer id;
	public String pageTitle;
	public String artifactCategory;
	public String tenantName;
	public String tenantId;
	private int artifactId;
	private LocalDateTime lastViewedOn;
	public int viewCount;
	
	
	
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public int getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}
	public LocalDateTime getLastViewedOn() {
		return lastViewedOn;
	}
	public void setLastViewedOn(LocalDateTime lastViewedOn) {
		this.lastViewedOn = lastViewedOn;
	}
	
	
	
}
