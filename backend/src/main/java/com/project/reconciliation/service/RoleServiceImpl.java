package com.project.reconciliation.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.reconciliation.entities.ERole;
import com.project.reconciliation.entities.Role;
import com.project.reconciliation.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleRepository repo;

	@Override
	public Optional<Role> findRoleByName(ERole role) {
		logger.info("Finding role by name: {}", role);
		Optional<Role> r = repo.findByName(role);
		return r;
	}

	@Override
	public Optional<Role> findRoleById(Integer id) {
		logger.info("Finding role by ID: {}", id);
		Optional<Role> r = repo.findById(id);
		return r;
	}

}
