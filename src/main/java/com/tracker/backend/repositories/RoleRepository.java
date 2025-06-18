package com.tracker.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tracker.backend.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
}
