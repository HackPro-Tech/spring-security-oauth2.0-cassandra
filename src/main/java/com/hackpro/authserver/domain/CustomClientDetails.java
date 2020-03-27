package com.hackpro.authserver.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@Getter
@Setter
public class CustomClientDetails implements ClientDetails {

	private static final long serialVersionUID = 1L;

	private String clientId;
	private String clientSecret;
	private String clientName;

	private Set<String> scope;
	private Set<String> resourceIds;
	private Set<String> authorizedGrantTypes;
	private Set<String> registeredRedirectUris;
	private List<GrantedAuthority> authorities;
	private Integer accessTokenValiditySeconds;
	private Integer refreshTokenValiditySeconds;
	private Map<String, Object> additionalInformation;
	private Set<String> autoApproveScopes;

	public CustomClientDetails(Client client) {
		this.clientId = client.getClientId();
		this.clientSecret = client.getClientSecret();
		this.clientName = client.getClientName();
		this.scope = client.getScope();
		this.resourceIds = client.getResourceIds();
		this.authorizedGrantTypes = client.getAuthorizedGrantTypes();
		this.registeredRedirectUris = client.getRegisteredRedirectUris();
		this.authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_CLIENT,ROLE_TRUSTED_CLIENT");
		this.accessTokenValiditySeconds = client.getAccessTokenValiditySeconds();
		this.refreshTokenValiditySeconds = client.getRefreshTokenValiditySeconds();
		this.additionalInformation = new HashMap<>();
		this.autoApproveScopes = client.getAutoApproveScopes();
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		return resourceIds;
	}

	@Override
	public boolean isSecretRequired() {
		return true;
	}

	@Override
	public String getClientSecret() {
		return clientSecret;
	}

	@Override
	public boolean isScoped() {
		return true;
	}

	@Override
	public Set<String> getScope() {
		return scope;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return registeredRedirectUris;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}
}
