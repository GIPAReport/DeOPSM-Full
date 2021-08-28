package com.designops.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Role")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {

	@Id
	@GeneratedValue
	@Column(name="roleId",unique=true)
	private int roleId;
	
	@Column(name="roleName")
	private String roleName;
	
	private String isActive;
	
	@Column(name="roleDescription")
	private String roleDescription;
	
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="RolePermission", joinColumns= @JoinColumn(name="roleId",foreignKey=@javax.persistence.ForeignKey(name="none")),inverseJoinColumns=@JoinColumn(name="permissionId",foreignKey=@javax.persistence.ForeignKey(name="none")))
	private List<RolePermission> rolePermission= new ArrayList<>();

	
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public List<RolePermission> getRolePermission() {
		return rolePermission;
	}

	public void setRolePermission(List<RolePermission> rolePermission) {
		this.rolePermission = rolePermission;
	}
	
	
	
}
