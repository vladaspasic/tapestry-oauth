package org.pac.tapestry.oauth.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.models.AccessToken;
import org.pac.tapestry.oauth.models.OAuthSession;
import org.pac.tapestry.oauth.services.AccessTokenExtractor;

/**
 * Default implementation of the {@link AccessTokenExtractor}.
 * <p>
 * Checks if the {@link OAuthSession} is set, and returns the {@link AccessToken};
 * <p>
 * If the {@link OAuthSession} does not exists or there is no {@link AccessToken}
 * stored in the session, it will proceed to the next configured {@link AccessTokenExtractor}
 * in the chain.
 * 
	@author vladimir
 */

public class SessionAccessTokenExtractor implements AccessTokenExtractor {

	@Inject
	private ApplicationStateManager manager;
	
	@Override
	public AccessToken get(OAuthClient client) {
		OAuthSession session = manager.getIfExists(OAuthSession.class);
		
		if(session == null)
			return null;
		
		return session.getAccessToken();
	}

}
