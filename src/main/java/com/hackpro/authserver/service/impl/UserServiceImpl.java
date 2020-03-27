package com.hackpro.authserver.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackpro.authserver.dao.UserDao;
import com.hackpro.authserver.domain.User;
import com.hackpro.authserver.domain.UserDetailsPrincipal;
import com.hackpro.authserver.service.UserService;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDetailsPrincipal getUserByEmailOrPhoneNo(String emailOrPhoneNo) {
		// fetch user based on Email Or Phone no
		Optional<User> user = userDao.findByEmailOrPhoneNo(emailOrPhoneNo);
		UserDetailsPrincipal userDetailsPrincipal = null;
		if (user.isPresent()) {
			userDetailsPrincipal = modelMapper.map(user.get(), UserDetailsPrincipal.class);
		}
		return userDetailsPrincipal;
	}
}
