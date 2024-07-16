package com.project.reconciliation.service;

import java.util.Optional;

import com.project.reconciliation.entities.Role;
import com.project.reconciliation.entities.UserEntity;

public interface UserEntityService {
	public UserEntity addUserEntity(UserEntity user);
	 
	public String updateRole(Integer userId, Role role);
 
	public Optional<UserEntity> findByUsername(String username);
 
	public Boolean existsByUsername(String username);
}
