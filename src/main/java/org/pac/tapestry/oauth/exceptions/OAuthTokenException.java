package org.pac.tapestry.oauth.exceptions;

import org.scribe.exceptions.OAuthException;

/**
 * Exception wrapped around {@link OAuthException}
 * 
	@author vladimir
 */

public class OAuthTokenException extends OAuthException {

	private static final long serialVersionUID = 4561839317942711028L;

	public OAuthTokenException() {
		super("No request token in the session.");
	}
	
	public OAuthTokenException(String message) {
		super(message);
	}
	
}
