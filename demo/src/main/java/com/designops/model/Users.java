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

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users {

	@Id
	@GeneratedValue
	@Column(name="userId")
	private int userId;
	
	@Column(name="email")
	private String email;
	
	@Column(name="name")
	private String name;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="TenantUser", joinColumns= @JoinColumn(name="userId",foreignKey=@javax.persistence.ForeignKey(name="none")),inverseJoinColumns=@JoinColumn(name="tenantId",foreignKey=@javax.persistence.ForeignKey(name="none")))
	private List<TenantUser> tenantUser= new ArrayList<>();
	
	private String isActive;
	
	private String password;
	
	private String userName;
	
	private String isSuperAdmin;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public List<TenantUser> getTenantUser() {
		return tenantUser;
	}

	public void setTenantUser(List<TenantUser> tenantUser) {
		this.tenantUser = tenantUser;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}
	
	
	
}
