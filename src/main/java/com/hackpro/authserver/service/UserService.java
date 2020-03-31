package com.hackpro.authserver.service;

import java.util.List;

import com.hackpro.authserver.bean.request.UserInfoRequest;
import com.hackpro.authserver.bean.request.UserSearchRequest;
import com.hackpro.authserver.bean.response.UserResponse;
import com.hackpro.authserver.domain.UserDetailsPrincipal;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
public interface UserService {

	public UserDetailsPrincipal getUserByEmailOrPhoneNo(String emailOrPhoneNo);

	public void registerNewUser(UserInfoRequest userInfoRequest);

	public List<UserResponse> fetchUsers(UserSearchRequest userSearchRequest);

}