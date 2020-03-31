package com.hackpro.authserver.bean.response;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Data
public class UserResponse {

	private UUID userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNo;
	@JsonAlias("active")
	private boolean enabled;
	private String profileImage;
	private List<String> roles;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;

}
