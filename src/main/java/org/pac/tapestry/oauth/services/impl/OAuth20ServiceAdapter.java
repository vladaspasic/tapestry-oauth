package org.pac.tapestry.oauth.services.impl;

import org.apache.tapestry5.services.Request;
import org.pac.tapestry.oauth.OAuthVersions;
import org.scribe.model.OAuthConstants;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
	@author vladimir
 */

public class OAuth20ServiceAdapter extends BaseOAuthServiceAdapter {
	
	@Override
	public String getVerifierCode(Request request) {

		return request.getParameter(OAuthConstants.CODE);
	}
	
	public Token generateRequestToken(OAuthService service) {
		
		return null;
	}

	@Override
	public OAuthVersions getVersion() {
		return OAuthVersions.V2_0;
	}
	
}