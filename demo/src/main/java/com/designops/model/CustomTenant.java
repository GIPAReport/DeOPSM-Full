package com.designops.model;

public class CustomTenant {

	private int roleId;
	
	private int tenantId;
	
	private String tenantName;
	
	private String roleName;
	
	private String artifactCategory;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getArtifactCategory() {
		return artifactCategory;
	}

	public void setArtifactCategory(String artifactCategory) {
		this.artifactCategory = artifactCategory;
	}
	
	
}
