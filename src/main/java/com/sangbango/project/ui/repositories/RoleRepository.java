package com.sangbango.project.ui.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangbango.project.ui.entitymodel.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	RoleEntity findByRoleName(String roleName);
	RoleEntity findByRoleId(String roleId);
}
