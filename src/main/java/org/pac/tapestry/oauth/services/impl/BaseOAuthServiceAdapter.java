package org.pac.tapestry.oauth.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Request;
import org.pac.tapestry.oauth.OAuthVersions;
import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.exceptions.OAuthTokenException;
import org.pac.tapestry.oauth.models.AccessToken;
import org.pac.tapestry.oauth.models.OAuthSession;
import org.pac.tapestry.oauth.services.OAuthServiceAdapter;
import org.scribe.model.OAuthConstants;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;

/**
	@author vladimir
 */

public abstract class BaseOAuthServiceAdapter implements OAuthServiceAdapter {

	@Inject
	private ApplicationStateManager applicationStateManager;
	
	@Inject
	private Logger logger;
	
	@Override
	public AccessToken retrieveAccessToken(OAuthClient client, Request request) {
		
		OAuthService service = client.getService();
		
		if(!checkVersion(service))
			return null;
		
		String requestToken = request.getParameter(OAuthConstants.TOKEN);
		
		String code = getVerifierCode(request);
		
		logger.info("Verifyng Callback Request with token {} and verifier {}", requestToken, code);
		
		Verifier verifier =  new Verifier(code);
		
		OAuthSession session = getSession();
		
		Token sessionToken = session.getToken();
		
		if(!session.isValidToken(requestToken))
			throw new OAuthTokenException("Request tokens do not match.");
		
		Token token = service.getAccessToken(sessionToken, verifier);
		
		AccessToken accessToken = new AccessToken(token.getToken(), token.getSecret());
		
		logger.info("Access Token obtained {}", accessToken);
		
		session.setAccessToken(accessToken);
		
		storeSession(session);
		
		return accessToken;
	}
	
	@Override
	public String getAuthorizationUrl(OAuthClient client) {
		
		OAuthService service = client.getService();
		
		if(!checkVersion(service))
			return null;
		
		Token token = generateRequestToken(service);
		
		logger.info("Request token obtained {}", token);
		
		OAuthSession session = new OAuthSession(token, getVersion());
		
		storeSession(session);
		
		String url = service.getAuthorizationUrl(token);
		
		logger.info("Authorization URL obtained {}", url);
		
		return url;
	}
	
	private boolean checkVersion(OAuthService service) {
		return getVersion().isMatchingVersion(service.getVersion());
		
	}
	
	protected void storeSession(OAuthSession session) {

		applicationStateManager.set(OAuthSession.class, session);
	}
	
	protected OAuthSession getSession() {
		
		OAuthSession oAuthSession = applicationStateManager.getIfExists(OAuthSession.class);
		
		if(oAuthSession == null)
			throw new OAuthTokenException();

		return oAuthSession;
	}
	
	public abstract OAuthVersions getVersion();
	
	public abstract String getVerifierCode(Request request);
	
	public abstract Token generateRequestToken(OAuthService service);
	
}
