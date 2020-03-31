package com.hackpro.authserver.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.CassandraConnectionFailureException;

import com.hackpro.authserver.domain.User;
import com.hackpro.authserver.exception.PersistentException;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
public interface UserDao {

	/**
	 * To fetch user based on Email Or Phone No
	 * 
	 * @param emailOrPhoneNo
	 * @return
	 */
	public Optional<User> findByEmailOrPhoneNo(String emailOrPhoneNo);

	/**
	 * To Save New User Details or Register New User
	 * 
	 * @param user
	 * @throws CassandraConnectionFailureException
	 * @throws PersistentException
	 */
	public void saveUserDetails(User user);

	/**
	 * To fetch All Users Information Based on Pagination criteria
	 * 
	 * @param reqRecordCount
	 * @param reqPageNo
	 * @return
	 */
	public List<User> getAllUsers(int reqRecordCount, int reqPageNo);

}