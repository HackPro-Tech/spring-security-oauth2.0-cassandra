
package com.hackpro.authserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hackpro.authserver.bean.request.UserInfoRequest;
import com.hackpro.authserver.bean.request.UserSearchRequest;
import com.hackpro.authserver.bean.response.UserResponse;
import com.hackpro.authserver.service.UserService;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@RestController
//@RequestMapping("/security")
public class UserSecurityController {

	@Autowired
	private UserService userService;

	/**
	 * To Register as new User
	 * 
	 * @param userInfoRequest
	 */
	@PostMapping("/register-new-user")
	public void registerNewUser(@RequestBody UserInfoRequest userInfoRequest) {
		userService.registerNewUser(userInfoRequest);
	}

	/**
	 * To fetch all User information
	 * 
	 * @param userSearchRequest
	 * @return the List of user information
	 */
	@PostMapping("/fetch-all-users")
	public ResponseEntity<List<UserResponse>> fetchUsers(@RequestBody UserSearchRequest userSearchRequest) {
		return ResponseEntity.ok(userService.fetchUsers(userSearchRequest));
	}

}
