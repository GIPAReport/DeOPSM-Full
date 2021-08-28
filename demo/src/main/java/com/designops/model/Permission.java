package com.designops.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Permission")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Permission {
	
	@Id
	@GeneratedValue
	@Column(name="permissionId",unique=true)
	private int permissionId;
	
	@Column(name="permissionName")
	private String permissionName;
	
	@Column(name="isActive")
	private String isActive;
	
	@ManyToMany
	@JoinTable(name="RolePermission",joinColumns= @JoinColumn(name="permissionId", foreignKey=@javax.persistence.ForeignKey(name="none")),inverseJoinColumns=@JoinColumn(name="roleId",foreignKey=@javax.persistence.ForeignKey(name="none")))
	private List<RolePermission> rolePermission = new  ArrayList<>();

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public List<RolePermission> getRolePermission() {
		return rolePermission;
	}

	public void setRolePermission(List<RolePermission> rolePermission) {
		this.rolePermission = rolePermission;
	}
	
	

}
