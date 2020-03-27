package com.hackpro.authserver.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.hackpro.authserver.domain.Role;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Repository
public interface RoleRepository extends CassandraRepository<Role, UUID> {

	Role findByRoleName(String roleName);

}
