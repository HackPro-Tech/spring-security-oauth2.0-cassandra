package com.hackpro.authserver.service.impl;

import java.util.Optional;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import com.hackpro.authserver.domain.Client;
import com.hackpro.authserver.domain.CustomClientDetails;
import com.hackpro.authserver.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author vengatesanns(HackPro)
 *
 */
@RequiredArgsConstructor
public class ClientDetailsServiceImpl implements ClientDetailsService {

	private final ClientRepository clientRepository;

	@Override
	public ClientDetails loadClientByClientId(String clientId) {
		Optional<Client> client = clientRepository.findByClientId(clientId);
		if (client.isPresent()) {
			return new CustomClientDetails(client.get());
		} else {
			throw new ClientRegistrationException(String.format("Client with id %s not found", clientId));
		}
	}
}
