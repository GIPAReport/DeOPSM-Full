package com.designops.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Tenant")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tenant {

	@Id
	@GeneratedValue
	@Column(name="tenantId")
	private int tenantId;
	
	@Column(name="tenantName")
	private String tenantName;
	
	@Column(name="isActive")
	private String isActive;
	
	public String getTenantDescription() {
		return tenantDescription;
	}

	public void setTenantDescription(String tenantDescription) {
		this.tenantDescription = tenantDescription;
	}

	public String getArtifactCategory() {
		return artifactCategory;
	}

	public void setArtifactCategory(String artifactCategory) {
		this.artifactCategory = artifactCategory;
	}

	private String tenantDescription;
	
	@NotBlank(message="Artifact Category is mandatory")
	private String artifactCategory;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="TenantUser", joinColumns= @JoinColumn(name="tenantId",foreignKey=@javax.persistence.ForeignKey(name="none")),inverseJoinColumns=@JoinColumn(name="userId",foreignKey=@javax.persistence.ForeignKey(name="none")))
	private List<TenantUser> tenantUser= new ArrayList<>();

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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public List<TenantUser> getTenantUser() {
		return tenantUser;
	}

	public void setTenantUser(List<TenantUser> tenantUser) {
		this.tenantUser = tenantUser;
	}
	
	
	
	
	
	
	
}
