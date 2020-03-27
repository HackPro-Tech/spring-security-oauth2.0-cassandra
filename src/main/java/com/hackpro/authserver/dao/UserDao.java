package com.hackpro.authserver.dao;

import java.util.Optional;

import com.hackpro.authserver.domain.User;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
public interface UserDao {

	public Optional<User> findByEmailOrPhoneNo(String emailOrPhoneNo);

}