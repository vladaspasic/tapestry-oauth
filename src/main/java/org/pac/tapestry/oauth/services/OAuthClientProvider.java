package org.pac.tapestry.oauth.services;

import java.util.List;

import org.apache.tapestry5.ioc.annotations.UsesConfiguration;
import org.pac.tapestry.oauth.clients.OAuthClient;

/**
 * Service used to obtain the {@link OAuthClient} from the configuration
 * 
	@author vladimir
 */
@UsesConfiguration(OAuthClient.class)
public interface OAuthClientProvider {

	OAuthClient get(String clientId);
	
	OAuthClient get(Class clientClass);
	
	List<OAuthClient> getSupportedClients();
	
}
