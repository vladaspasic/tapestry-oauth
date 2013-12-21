package org.pac.tapestry.oauth.models;

import org.pac.tapestry.oauth.OAuthVersions;
import org.scribe.model.Token;


/**
 * Session State Object for Authenticating and obtaining User Tokens
 * 
	@author vladimir
 */

public class OAuthSession {

	private AccessToken accessToken;
	
	private final Token token;
	
	private final OAuthVersions version;
	
	public OAuthSession(Token token, OAuthVersions version) {
		this.token = token;
		this.version = version;
	}

	public Token getToken() {
		return token;
	}
	
	public boolean isValidToken(String token) {
		
		if(version.equals(OAuthVersions.V2_0))
			return true;
		
		return this.token.getToken().equals(token);
	}
	
	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}
	
	public AccessToken getAccessToken() {
		return accessToken;
	}

	public boolean isDeniedAccess() {
		return accessToken == null;
	}

	@Override
	public String toString() {
		return "OAuthSession [token=" + token + "]";
	}
	
}
