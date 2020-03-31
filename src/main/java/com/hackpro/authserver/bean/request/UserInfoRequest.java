package com.hackpro.authserver.bean.request;

import lombok.Data;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Data
public class UserInfoRequest {

	private String firstName;
	private String lastName;
	private String email;
	private String phoneNo;
	private String dateOfBirth;
	private String password;
	private String profileImage;

}
