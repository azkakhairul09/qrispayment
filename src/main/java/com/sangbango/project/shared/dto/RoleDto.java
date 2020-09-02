package com.sangbango.project.shared.dto;

import java.util.Set;

public class RoleDto {
	private Long id;
	private String roleId;
	private String roleName;
	private Set<UserDto> user;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Set<UserDto> getUser() {
		return user;
	}
	public void setUser(Set<UserDto> user) {
		this.user = user;
	}
}
