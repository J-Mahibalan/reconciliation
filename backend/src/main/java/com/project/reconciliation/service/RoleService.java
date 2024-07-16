package com.project.reconciliation.service;

import java.util.Optional;

import com.project.reconciliation.entities.ERole;
import com.project.reconciliation.entities.Role;

public interface RoleService {
	public Optional<Role> findRoleByName(ERole role);
	public Optional<Role> findRoleById(Integer id);
}
