package com.project.reconciliation.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.reconciliation.entities.ERole;
import com.project.reconciliation.entities.Role;


@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
  Optional<Role> findByName(ERole name);
}
