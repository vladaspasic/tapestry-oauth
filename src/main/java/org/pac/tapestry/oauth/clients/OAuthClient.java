package org.pac.tapestry.oauth.clients;

import org.scribe.oauth.OAuthService;


/**
	@author vladimir
 */

public interface OAuthClient {

	OAuthService getService();
	
	Class getApi();
	
	String getClientId();
	
	String getName();
	
}
