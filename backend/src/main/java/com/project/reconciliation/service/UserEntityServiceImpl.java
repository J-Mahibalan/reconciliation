package com.project.reconciliation.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.reconciliation.entities.Role;
import com.project.reconciliation.entities.UserEntity;
import com.project.reconciliation.repositories.UserRepository;

@Service
public class UserEntityServiceImpl implements UserEntityService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserEntityServiceImpl.class);

	@Autowired
	private UserRepository repo;

	@Override
	public String updateRole(Integer userId, Role role) {
		logger.info("Updating role for user ID: {}", userId);
		Optional<UserEntity> user= repo.findById(userId);
		if(user.isPresent())
		{
			user.get().setRole(role);
			repo.save(user.get());
			logger.info("Role updated successfully for user ID: {}", userId);
			return "Role Updated Successfully!!!";
		}
		logger.warn("User not found for ID: {}", userId);
		return null;
	}

	@Override
	public UserEntity addUserEntity(UserEntity user) {
		logger.info("Adding new user entity: {}", user.getUsername());
		UserEntity userEntity=repo.save(user);
		return userEntity;
	}

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		logger.info("Finding user by username: {}", username);
		Optional<UserEntity>  user =repo.findByUsername(username);
		return user;
	}

	@Override
	public Boolean existsByUsername(String username) {
		logger.info("Checking if username exists: {}", username);
		Boolean b=repo.existsByUsername(username);
		return b;
	}
}
