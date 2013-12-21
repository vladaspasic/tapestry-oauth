package org.pac.tapestry.oauth.services;

import org.apache.tapestry5.services.Request;
import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.models.AccessToken;

/**
 * Adapter built around the OAuth Scribe Plugin, main role is to provide access to different
 * Versions of OAuth.
 * 
 *
	@author vladimir
 */

public interface OAuthServiceAdapter {

	public String getAuthorizationUrl(OAuthClient client);
	
	public AccessToken retrieveAccessToken(OAuthClient client, Request request);
	
}
