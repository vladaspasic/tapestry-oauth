package org.pac.tapestry.oauth.services.impl;

import java.io.IOException;

import org.apache.tapestry5.internal.EmptyEventContext;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.pac.tapestry.oauth.OAuthTapestryConstants;
import org.pac.tapestry.oauth.OAuthSymbolConstants;
import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.models.AccessToken;
import org.pac.tapestry.oauth.models.OAuthSession;
import org.pac.tapestry.oauth.services.AuthenticationSuccessHandler;
import org.pac.tapestry.oauth.services.OAuthClientProvider;
import org.pac.tapestry.oauth.services.OAuthServiceAdapter;
import org.scribe.exceptions.OAuthException;
import org.slf4j.Logger;

/**
 * 
 * Dispatcher for handling OAuth callbacks and rendering the page that will close
 * the popup window.
 * <p> 
 * It will only work if the {@link OAuthSession} exists, in order to compare Request Tokens.
 * <p>
 * If the token from {@link Request} matches the token from the {@link OAuthSession} it
 * will extract the verifier code and obtain {@link AccessToken}.
 * <p>
 * In case the request contains the denied parameter, it will just proceed to render the response
 * page which will close the popup, and put the denied parameter in the session
 * 
	@author vladimir
 */

public class CallbackDispatcher implements Dispatcher {

	@Inject
	private OAuthClientProvider authClientProvider;
	
	@Inject
	private OAuthServiceAdapter oAuthServiceAdapter;
	
	@Inject
	private ComponentRequestHandler handler;
	
	@Inject
	private AuthenticationSuccessHandler successHandler;
	
	@Inject
	@Symbol(OAuthSymbolConstants.OAUTH_CALLBACK_PATH)
	private String callbackPath;
	
	@Inject
	private Logger logger;
	
	@Override
	public boolean dispatch(Request request, Response response) throws IOException {
		String path = request.getPath();
		
		if(!path.contains(callbackPath))
			return false;
		
		String clientId = request.getParameter(OAuthTapestryConstants.OAUTH_CLIENT_ID_PARAMETER);
		
		boolean isDenied = request.getParameter(OAuthTapestryConstants.OAUTH_DENIED_PARAMETER) != null;
		
		if(!isDenied) {
			OAuthClient client = authClientProvider.get(clientId);
			
			try {
				
				AccessToken tokens = oAuthServiceAdapter.retrieveAccessToken(client, request);
				
				successHandler.handle(client, tokens, request);
				
			} catch (OAuthException e) {
				logger.error("Error while obtaining AccessTokens", e);
			}
			
		}
		
		PageRenderRequestParameters params = new PageRenderRequestParameters("oauth/CallbackLandingPage", new EmptyEventContext(), false);
		
		handler.handlePageRender(params);
		
		return true;
	}
	
}
