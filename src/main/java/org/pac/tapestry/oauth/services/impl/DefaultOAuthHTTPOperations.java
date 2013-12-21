package org.pac.tapestry.oauth.services.impl;

import java.util.Iterator;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.models.AccessToken;
import org.pac.tapestry.oauth.models.OAuthResponse;
import org.pac.tapestry.oauth.services.AccessTokenExtractor;
import org.pac.tapestry.oauth.services.OAuthHTTPOperations;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;

/**
	@author vladimir
 */

public class DefaultOAuthHTTPOperations implements OAuthHTTPOperations {
	
	@Inject
	private AccessTokenExtractor accessTokenExtractor;

	@Override
	public OAuthResponse process(OAuthClient client, String url, String... parameters) {
		return process(client,Verb.GET, url, parameters);
	}
	
	@Override
	public OAuthResponse process(OAuthClient client, Verb verb, String url, String... parameters) {
		
		AccessToken accessToken = accessTokenExtractor.get(client);
		
		OAuthRequest request = new OAuthRequest(verb, url);
		
		Token token = new Token(accessToken.getToken(), accessToken.getSecret());
		
		List<String> params = CollectionFactory.newList(parameters);
		
		Iterator<String> iterator = params.listIterator();
		
		while(iterator.hasNext()) {
			
			String key = iterator.next();
			
			String value = iterator.next();
			
			request.addQuerystringParameter(key, value);
		}
		
		client.getService().signRequest(token, request);
		
		return new OAuthResponse(request);
	}
	
	@Override
	public OAuthResponse process(OAuthRequest request) {
		
		return new OAuthResponse(request);
	}
	
}
