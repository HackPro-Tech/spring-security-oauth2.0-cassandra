package com.hackpro.authserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackpro.authserver.domain.AccessToken;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Repository
public interface AccessTokenRepository extends CassandraRepository<AccessToken, String> {

	Optional<AccessToken> findByTokenId(String tokenId);

//	Materialized Views
	@Query("select * from access_token_by_authentication_id where authentication_id =:authenticationId ")
	Optional<AccessToken> findByAuthenticationId(@Param("authenticationId") String authenticationId);

	@Query("select * from access_token where access_token_by_refresh_token =:refreshToken ")
	Optional<AccessToken> findByRefreshToken(@Param("refreshToken") String refreshToken);

	@Query("select * from access_token_by_clientid_username where clientid =:clientId ")
	List<AccessToken> findByClientId(@Param("client_id") String clientId);

	@Query("select * from access_token_by_clientid_username where client_id =:clientId and username =:username ")
	List<AccessToken> findByClientIdAndUsername(@Param("client_id") String clientId,
			@Param("username") String username);
}
