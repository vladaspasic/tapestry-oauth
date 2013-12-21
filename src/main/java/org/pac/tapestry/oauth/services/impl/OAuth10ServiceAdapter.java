package org.pac.tapestry.oauth.services.impl;

import org.apache.tapestry5.services.Request;
import org.pac.tapestry.oauth.OAuthVersions;
import org.scribe.model.OAuthConstants;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
	@author vladimir
 */

public class OAuth10ServiceAdapter extends BaseOAuthServiceAdapter {
	
	@Override
	public String getVerifierCode(Request request) {
		
		return request.getParameter(OAuthConstants.VERIFIER);
	}
	
	public Token generateRequestToken(OAuthService service) {
		
		return service.getRequestToken();
	}

	@Override
	public OAuthVersions getVersion() {
		return OAuthVersions.V1_0;
	}
	
}