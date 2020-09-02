package com.sangbango.project.ui.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangbango.project.ui.entitymodel.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	UserEntity findByEmail(String email);
	UserEntity findUserByUserId(String userId);
	UserEntity findRoleByUserId(String userId);
	List<UserEntity> findUserByIsActiveOrderByCreatedDateDesc(boolean isActive);
	UserEntity findByUserId(String userId);
}
