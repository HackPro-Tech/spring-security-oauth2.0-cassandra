package com.hackpro.authserver.domain;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Data
@Table(value = "role")
public class Role {

	@PrimaryKey("role_id")
	private UUID roleId;

	@Column("role_name")
	private String roleName;
}
