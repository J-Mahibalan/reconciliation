package com.project.reconciliation.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.reconciliation.entities.ERole;
import com.project.reconciliation.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	public Optional<UserEntity> findByUsername(String username);

	public Boolean existsByUsername(String username);

	public Boolean existsByEmail(String email);

//	public Optional<UserEntity> findByRole(ERole role);
}

