package com.hackpro.authserver.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hackpro.authserver.bean.request.UserInfoRequest;
import com.hackpro.authserver.bean.request.UserSearchRequest;
import com.hackpro.authserver.bean.response.UserResponse;
import com.hackpro.authserver.constants.UserRoles;
import com.hackpro.authserver.dao.UserDao;
import com.hackpro.authserver.domain.User;
import com.hackpro.authserver.domain.UserDetailsPrincipal;
import com.hackpro.authserver.exception.MaxRecordLimitException;
import com.hackpro.authserver.exception.ServiceException;
import com.hackpro.authserver.exception.UserAlreadyExistsException;
import com.hackpro.authserver.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Environment Variables
	 */
	@Value("${records.max-limit}")
	private int maxLimit;

	@Value("${records.initial-count}")
	private int initialCount;

	@Value("${records.page-no}")
	private int pageNo;

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

	@Override
	public void registerNewUser(UserInfoRequest userInfoRequest) {
		try {
			if (checkUserAlreadyExists(userInfoRequest)) {
				throw new UserAlreadyExistsException("User Already Exists!!!");
			} else {
				User user = mapUserRequest(userInfoRequest);
				userDao.saveUserDetails(user);
			}
		} catch (UserAlreadyExistsException userException) {
			log.debug("##### Current User is already exists ", userException);
			throw userException;
		} catch (Exception ex) {
			log.debug("##### Error while register new user ", ex);
			throw new ServiceException("Error while register new user");
		}
	}

	private boolean checkUserAlreadyExists(UserInfoRequest userInfoRequest) {
		Optional<User> user = userDao.findByEmailOrPhoneNo(userInfoRequest.getEmail());
		return user.isPresent();
	}

	private User mapUserRequest(UserInfoRequest userInfoRequest) {
		User user = new User();
		user.setFirstName(userInfoRequest.getFirstName());
		user.setLastName(userInfoRequest.getLastName());
		user.setEmail(userInfoRequest.getEmail());
		user.setPhoneNo(userInfoRequest.getPhoneNo());
		user.setUserId(UUID.randomUUID());
		user.setPassword(userInfoRequest.getPassword());
		user.setProfileImage(userInfoRequest.getProfileImage());
		user.setEnabled(true);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setRoles(Arrays.asList(UserRoles.ADMIN.getRole(), UserRoles.USER.getRole()));
		return user;
	}

	@Override
	public List<UserResponse> fetchUsers(UserSearchRequest userSearchRequest) {
		List<UserResponse> usersResponse = new ArrayList<>();
		try {
			if (maxLimit >= userSearchRequest.getRecordsCount()) {
				int reqRecordCount = userSearchRequest.getRecordsCount() <= 0 ? initialCount
						: userSearchRequest.getRecordsCount();
				int reqPageNo = userSearchRequest.getPageNo() < 0 ? pageNo : userSearchRequest.getPageNo();
				List<User> usersInfo = userDao.getAllUsers(reqRecordCount, reqPageNo);
				modelMapper.map(usersInfo, usersResponse);
			} else {
				throw new MaxRecordLimitException("Maximum Limit Count reached (max_record_count = " + maxLimit + " )");
			}
		} catch (MaxRecordLimitException maxLimitException) {
			log.debug("##### Error Maximum Limit Count for Record Reached!!! ", maxLimitException);
			throw maxLimitException;
		} catch (Exception ex) {
			log.debug("##### Error while fetching users information in method => fetchUsers() ", ex);
			throw new ServiceException("Error while fetching users information");
		}
		return usersResponse;
	}

}
