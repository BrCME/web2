package com.web2.safia.repositories.adapters;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web2.safia.models.Role;

public interface JpaRoleRepository extends JpaRepository<Role, UUID> {
}
