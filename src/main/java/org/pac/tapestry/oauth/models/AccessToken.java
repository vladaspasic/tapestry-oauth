package org.pac.tapestry.oauth.models;


/**
 * Wrapper For OAuth AccessTokes, contains Token and Secret Token
 *
	@author vladimir
 */

public class AccessToken {

	private final String token;
	
	private final String secret;
	
	public AccessToken(String token, String secret) {
		this.token = token;
		this.secret = secret;
	}

	public String getToken() {
		return token;
	}

	public String getSecret() {
		return secret;
	}

	@Override
	public String toString() {
		return "AccessToken [token=" + token + ", secret=" + secret + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((secret == null) ? 0 : secret.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AccessToken))
			return false;
		AccessToken other = (AccessToken) obj;
		if (secret == null) {
			if (other.secret != null)
				return false;
		} else if (!secret.equals(other.secret))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
}
