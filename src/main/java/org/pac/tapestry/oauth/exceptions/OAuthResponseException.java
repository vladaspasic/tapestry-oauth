package org.pac.tapestry.oauth.exceptions;

import org.scribe.exceptions.OAuthException;
import org.scribe.model.Response;

/**
	@author vladimir
 */

public class OAuthResponseException extends OAuthException {

	private static final long serialVersionUID = -8423804027394029651L;

	private Response response;
	
	public OAuthResponseException(Response response) {
		super(String.format("Response returned with %s %s", response.getMessage(), response.getCode()));
		
		this.response = response;
	}
	
	public String getBody() {
		return response.getBody();
	}
	
	public String getMessage() {
		return response.getMessage();
	}
	
	public int getCode() {
		return response.getCode();
	}
	
}
