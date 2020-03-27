package com.hackpro.authserver.domain;

import java.util.List;
import java.util.Set;

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
@Table("client")
public class Client {

	@PrimaryKey("client_id")
	private String clientId;

	@Column("client_secret")
	private String clientSecret;

	@Column("client_name")
	private String clientName;

	@Column("scope")
	private Set<String> scope;

	@Column("resource_ids")
	private Set<String> resourceIds;

	@Column("authorized_grant_types")
	private Set<String> authorizedGrantTypes;

	@Column("registered_redirect_uris")
	private Set<String> registeredRedirectUris;

	@Column("authorities")
	private List<String> authorities;

	@Column("accesstoken_validity_seconds")
	private Integer accessTokenValiditySeconds;

	@Column("refreshtoken_validity_seconds")
	private Integer refreshTokenValiditySeconds;

	@Column("additional_information")
	private String additionalInformation;

	@Column("auto_approve_scopes")
	private Set<String> autoApproveScopes;

}
