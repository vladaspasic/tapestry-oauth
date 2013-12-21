package org.pac.tapestry.oauth.services;

import org.apache.tapestry5.ioc.annotations.UsesMappedConfiguration;
import org.apache.tapestry5.ioc.services.StrategyBuilder;
import org.apache.tapestry5.services.Request;
import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.models.AccessToken;
import org.pac.tapestry.oauth.services.impl.CallbackDispatcher;

/**
 * Interface for completing the OAuth process. This service is built using {@link StrategyBuilder}.
 * <p>
 * Strategy is the {@link OAuthClient} so each Client has it's own implementation of the service.
 * Not all OAuth providers have the same API for handling User profile data.
 * <p>
 * Called in the {@link CallbackDispatcher} after a successful authentication. Main use
 * is to persist the values from the OAuth Provider in the DB and handle the final part
 * of user identification
 * 
	@author vladimir
 */
@UsesMappedConfiguration(key = OAuthClient.class, value = AuthenticationSuccessHandler.class)
public interface AuthenticationSuccessHandler<Client> {

	public void handle(Client client, AccessToken accessToken, Request request);
	
}
