package com.hackpro.authserver.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hackpro.authserver.dao.UserDao;
import com.hackpro.authserver.domain.User;
import com.hackpro.authserver.repository.UserRepository;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserRepository userRepository;

	// fetch user based on Email Or Phone no
	@Override
	public Optional<User> findByEmailOrPhoneNo(String emailOrPhoneNo) {
		Optional<User> user = userRepository.findByEmail(emailOrPhoneNo);
		if (!user.isPresent()) {
			user = userRepository.findByPhoneNo(emailOrPhoneNo);
		}
		return user;
	}
}
