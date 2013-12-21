package org.pac.tapestry.oauth.services.impl;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.services.OAuthClientProvider;

/**
	@author vladimir
 */

public class ClientValueEncoder implements ValueEncoder<OAuthClient> {

	@Inject
	private OAuthClientProvider oAuthClientProvider;
	
	@Override
	public String toClient(OAuthClient value) {

		return value.getClientId();
	}
	
	@Override
	public OAuthClient toValue(String clientValue) {
		
		return oAuthClientProvider.get(clientValue);
	}
	
}
