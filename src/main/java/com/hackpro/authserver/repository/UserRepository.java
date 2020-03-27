package com.hackpro.authserver.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackpro.authserver.domain.User;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {

	@Query("select * from user_details_by_email where email =:email ")
	Optional<User> findByEmail(@Param("email") String email);

	@Query("select * from user_details_by_phoneno where phone_no =:phoneNo ")
	Optional<User> findByPhoneNo(@Param("phoneNo") String phoneNo);

}
