package com.hackpro.authserver.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.hackpro.authserver.domain.Approval;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Repository
public interface ApprovalRepository extends CassandraRepository<Approval, UUID> {
}
