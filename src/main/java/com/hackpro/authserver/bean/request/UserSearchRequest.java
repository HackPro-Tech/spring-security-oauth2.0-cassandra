package com.hackpro.authserver.bean.request;

import lombok.Data;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Data
public class UserSearchRequest {

	private int pageNo;
	private int recordsCount;

}
