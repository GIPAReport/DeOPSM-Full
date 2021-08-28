package com.designops.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="RolePermission")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RolePermission {

	
	@Id
	@GeneratedValue
	@Column(name="rolePermissionId")
	private int rolePermissionId;
	
	@Column(name="roleId")
	private int roleId;
	
	@Column(name="permissionId")
	private int permissionId;
	
	@Column(name="isActive")
	private String isActive;

	public int getRolePermissionId() {
		return rolePermissionId;
	}

	public void setRolePermissionId(int rolePermissionId) {
		this.rolePermissionId = rolePermissionId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	
	
	
}
