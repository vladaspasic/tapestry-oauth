package org.pac.tapestry.oauth.services;

import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.models.OAuthResponse;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;

/**
	@author vladimir
 */

public interface OAuthHTTPOperations {
	
	public OAuthResponse process(OAuthRequest request);

	public OAuthResponse process(OAuthClient client, String url, String... parameters);
	
	public OAuthResponse process(OAuthClient client, Verb verb, String url, String... parameters);
	
}
