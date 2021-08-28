package com.designops.model;

import java.util.List;

/**
 * @author Admin
 *
 */
public class ArtifactMenu {
	
	
private String artifactName;

private String name;

private int userId;

private String isSuperAdmin;

private List<CustomTenant> customTenantList;

public String getArtifactName() {
	return artifactName;
}

public void setArtifactName(String artifactName) {
	this.artifactName = artifactName;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getUserId() {
	return userId;
}

public void setUserId(int userId) {
	this.userId = userId;
}

public String getIsSuperAdmin() {
	return isSuperAdmin;
}

public void setIsSuperAdmin(String isSuperAdmin) {
	this.isSuperAdmin = isSuperAdmin;
}

public List<CustomTenant> getCustomTenantList() {
	return customTenantList;
}

public void setCustomTenantList(List<CustomTenant> customTenantList) {
	this.customTenantList = customTenantList;
}


	

}
