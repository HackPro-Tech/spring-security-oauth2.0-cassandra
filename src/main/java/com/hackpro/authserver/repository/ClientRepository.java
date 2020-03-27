package com.hackpro.authserver.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.hackpro.authserver.domain.Client;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Repository
public interface ClientRepository extends CassandraRepository<Client, String> {

	Optional<Client> findByClientId(String clientId);

}
