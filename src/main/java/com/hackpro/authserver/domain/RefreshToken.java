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
@Table(value = "refresh_token")
public class RefreshToken {

	@PrimaryKey("token_id")
	private String tokenId;

	@Column("token")
	private ByteBuffer token;

	@Column("authentication")
	private ByteBuffer authentication;

}
