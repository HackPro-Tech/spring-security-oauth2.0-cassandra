package com.hackpro.authserver.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType.Name;

import lombok.Data;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Data
@Table(value = "user")
public class User {

	@PrimaryKey("user_id")
	private UUID userId;

	@Column("username")
	private String username;

	@Column("firstname")
	private String firstName;

	@Column("lastname")
	private String lastName;

	@Column("email")
	private String email;

	@Column("password")
	private String password;

	@Column("phone_no")
	private String phoneNo;

	@Column("active")
	private boolean enabled;

	@Column("profile_image")
	@CassandraType(type = Name.BLOB)
	private String profileImage;

	@Column("roles")
	private List<String> roles;

	@Column("account_non_expired")
	private boolean accountNonExpired;

	@Column("account_non_locked")
	private boolean accountNonLocked;

	@Column("credentials_non_expired")
	private boolean credentialsNonExpired;

}
