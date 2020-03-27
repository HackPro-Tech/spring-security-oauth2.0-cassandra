package com.hackpro.authserver.security;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.hackpro.authserver.domain.AccessToken;
import com.hackpro.authserver.domain.RefreshToken;
import com.hackpro.authserver.repository.AccessTokenRepository;
import com.hackpro.authserver.repository.RefreshTokenRepository;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
public class CassandraTokenStore implements TokenStore {

	@Autowired
	private AccessTokenRepository accessTokenRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private AuthenticationKeyGenerator authenticationKeyGenerator;

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		Optional<AccessToken> accessToken = accessTokenRepository.findByTokenId(token);
		if (accessToken.isPresent()) {
			try {
				return deserializeAuthentication(accessToken.get().getAuthentication());
			} catch (IllegalArgumentException e) {
				removeAccessToken(token);
			}
		}
		return null;
	}

	// Store Access Token, remove Existing Token and store Refresh token
	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		ByteBuffer bufferedToken = serializeAccessToken(token);
		ByteBuffer bufferedAuthentication = serializeAuthentication(authentication);
		String refreshToken = null;
		if (token.getRefreshToken() != null) {
			refreshToken = token.getRefreshToken().getValue();
		}
		if (readAccessToken(token.getValue()) != null) {
			removeAccessToken(token.getValue());
		}
		AccessToken accessToken = new AccessToken();
		accessToken.setTokenId(token.getValue());
		accessToken.setToken(bufferedToken);
		accessToken.setAuthentication(bufferedAuthentication);
		accessToken.setRefreshToken(refreshToken);
		accessToken.setClientId(authentication.getOAuth2Request().getClientId());
		accessToken.setUsername(authentication.isClientOnly() ? null : authentication.getName());
		accessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
		accessTokenRepository.save(accessToken);
		if (token.getRefreshToken() != null && token.getRefreshToken().getValue() != null) {
			storeRefreshToken(token.getRefreshToken(), authentication);
		}
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenId) {
		Optional<AccessToken> accessToken = accessTokenRepository.findByTokenId(tokenId);
		if (accessToken.isPresent()) {
			try {
				return deserializeAccessToken(accessToken.get().getToken());
			} catch (IllegalArgumentException ex) {
				removeAccessToken(tokenId);
			}
		}
		return null;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		removeAccessToken(token.getValue());
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		ByteBuffer bufferedRefreshToken = serializeRefreshToken(refreshToken);
		ByteBuffer bufferedAuthentication = serializeAuthentication(authentication);
		final String tokenKey = refreshToken.getValue();
		RefreshToken rfToken = new RefreshToken();
		rfToken.setTokenId(tokenKey);
		rfToken.setToken(bufferedRefreshToken);
		rfToken.setAuthentication(bufferedAuthentication);
		refreshTokenRepository.save(rfToken);
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenId) {
		RefreshToken refreshToken = refreshTokenRepository.findByTokenId(tokenId);
		if (refreshToken != null) {
			try {
				return deserializeRefreshToken(refreshToken.getToken());
			} catch (IllegalArgumentException e) {
				removeRefreshToken(tokenId);
			}
		}
		return null;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		RefreshToken refreshToken = refreshTokenRepository.findByTokenId(token.getValue());
		if (refreshToken != null) {
			try {
				return deserializeAuthentication(refreshToken.getAuthentication());
			} catch (IllegalArgumentException e) {
				removeRefreshToken(token.getValue());
			}
		}
		return null;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		removeRefreshToken(token.getValue());
	}

	// Remove Access Token Using Refresh Token
	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		Optional<AccessToken> accessToken = accessTokenRepository.findByRefreshToken(refreshToken.getValue());
		if (accessToken.isPresent()) {
			accessTokenRepository.delete(accessToken.get());
		}
	}

	// Get Access Token based on Authetication ID
	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		String authenticationId = authenticationKeyGenerator.extractKey(authentication);
		Optional<AccessToken> accessToken = accessTokenRepository.findByAuthenticationId(authenticationId);
		if (accessToken.isPresent()) {
			return deserializeAccessToken(accessToken.get().getToken());
		}
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		final List<AccessToken> oAuth2AccessTokens = accessTokenRepository.findByClientIdAndUsername(userName,
				clientId);
		final Collection<AccessToken> noNullTokens = filter(oAuth2AccessTokens, byNotNulls());
		return transform(noNullTokens, toOAuth2AccessToken());
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		final List<AccessToken> oAuth2AccessTokens = accessTokenRepository.findByClientId(clientId);
		final Collection<AccessToken> noNullTokens = filter(oAuth2AccessTokens, byNotNulls());
		return transform(noNullTokens, toOAuth2AccessToken());
	}

	public void removeAccessToken(final String tokenValue) {
		Optional<AccessToken> accessToken = accessTokenRepository.findByTokenId(tokenValue);
		if (accessToken.isPresent()) {
			accessTokenRepository.delete(accessToken.get());
		}
	}

	public void removeRefreshToken(String token) {
		RefreshToken refreshToken = refreshTokenRepository.findByTokenId(token);
		if (refreshToken != null) {
			refreshTokenRepository.delete(refreshToken);
		}
	}

	/*
	 * public void removeAccessTokenUsingRefreshToken(final String refreshToken) {
	 * AccessToken accessToken =
	 * accessTokenRepository.findByRefreshToken(refreshToken); if (accessToken !=
	 * null) { accessTokenRepository.delete(accessToken); } }
	 */

	protected ByteBuffer serializeAccessToken(OAuth2AccessToken token) {
		return ByteBuffer.wrap(SerializationUtils.serialize(token));
	}

	protected ByteBuffer serializeRefreshToken(OAuth2RefreshToken token) {
		return ByteBuffer.wrap(SerializationUtils.serialize(token));
	}

	protected ByteBuffer serializeAuthentication(OAuth2Authentication authentication) {
		return ByteBuffer.wrap(SerializationUtils.serialize(authentication));
	}

	protected OAuth2AccessToken deserializeAccessToken(ByteBuffer token) {
		byte[] bytes = new byte[token.remaining()];
		token.get(bytes);
		return SerializationUtils.deserialize(bytes);
	}

	protected OAuth2RefreshToken deserializeRefreshToken(ByteBuffer token) {
		byte[] bytes = new byte[token.remaining()];
		token.get(bytes);
		return SerializationUtils.deserialize(bytes);
	}

	protected OAuth2Authentication deserializeAuthentication(ByteBuffer authentication) {
		byte[] bytes = new byte[authentication.remaining()];
		authentication.get(bytes);
		return SerializationUtils.deserialize(bytes);
	}

	private Predicate<AccessToken> byNotNulls() {
		return token -> token != null;
	}

	private Function<AccessToken, OAuth2AccessToken> toOAuth2AccessToken() {
		return token -> deserializeAccessToken(token.getToken());
	}

}
