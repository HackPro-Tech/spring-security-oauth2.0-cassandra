package com.hackpro.authserver.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hackpro.authserver.domain.UserDetailsPrincipal;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
public interface UserService {

	public UserDetailsPrincipal getUserByEmailOrPhoneNo(String emailOrPhoneNo) throws UsernameNotFoundException;

}