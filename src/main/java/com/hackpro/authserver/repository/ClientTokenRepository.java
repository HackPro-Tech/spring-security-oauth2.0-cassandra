package com.hackpro.authserver.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.hackpro.authserver.domain.ClientToken;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Repository
public interface ClientTokenRepository extends CassandraRepository<ClientToken, String> {
}
