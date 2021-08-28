package com.designops.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="TenantUser")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TenantUser {

	@Id
	@GeneratedValue
	private Integer tenantUserRoleId;
	
	@Column(name="tenantId")
	private Integer tenantId;
	
	@Column(name="userId")
	private Integer userId;
	
	@Column(name="roleId")
	private Integer roleId;
	
	@Column(name="roleName")
	private String roleName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="name")
	private String name;
	

	private String isActive;
	
    @Transient
	private String tenantName;

	public Integer getTenantUserRoleId() {
		return tenantUserRoleId;
	}

	public void setTenantUserRoleId(Integer tenantUserRoleId) {
		this.tenantUserRoleId = tenantUserRoleId;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}




	
	
}
