package org.pac.tapestry.oauth.clients;

import org.pac.tapestry.oauth.services.CallbackService;
import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

/**
	@author vladimir
 */

public abstract class BaseClient implements OAuthClient {

	private final OAuthService service;
	
	public BaseClient(CallbackService callbackService) {
		
		String callback = callbackService.create(getClientId());
		
		service = new ServiceBuilder()
					.provider(getApi())
					.apiKey(getKey())
					.apiSecret(getSecret())
					.callback(callback)				  
					.build();
	}
	
	@Override
	public OAuthService getService() {
		return service;
	}
	
	@Override
	public String getClientId() {
		return getApi().getSimpleName();
	}
	
	public abstract String getKey();
	
	public abstract String getSecret();

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + getClientId() + ", name=" + getName() + "]";
	}
	
}
