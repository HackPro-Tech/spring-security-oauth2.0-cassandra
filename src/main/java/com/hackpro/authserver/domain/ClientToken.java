package com.hackpro.authserver.domain;

import java.nio.ByteBuffer;

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
@Table(value = "client_token")
public class ClientToken {

	@PrimaryKey("token_id")
	private String tokenId;

	@Column("token")
	private ByteBuffer token;

	@Column("authentication_id")
	private String authenticationId;

	@Column("username")
	private String username;

	@Column("client_id")
	private String clientId;

}
