package com.sangbango.project.ui.entitymodel;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity(name="t_role")
public class RoleEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3012256330776073746L;
	
	@Id
	@TableGenerator(name="t_role", table="sequence_id",
	pkColumnName="sequence_name", pkColumnValue="roleID",
	valueColumnName="sequence_value", allocationSize =1, initialValue=0)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="t_role")
	private Long id;
	@Column(nullable = false, length = 25)
	private String roleId;
	@Column(nullable = false, length = 25)
	private String roleName;
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private Set<UserEntity> user;
	
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

	public Set<UserEntity> getUser() {
		return user;
	}

	public void setUser(Set<UserEntity> user) {
		this.user = user;
	}
}
