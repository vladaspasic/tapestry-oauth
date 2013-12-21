package org.pac.tapestry.oauth.services;

import org.apache.tapestry5.ioc.annotations.UsesOrderedConfiguration;
import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.models.AccessToken;
import org.pac.tapestry.oauth.models.OAuthSession;
import org.pac.tapestry.oauth.services.impl.SessionAccessTokenExtractor;

/**
 * Service for extracting already obtained {@link AccessToken} from OAuth.
 * This is a chain of services, which are ordered, so you can have multiple
 * implementation of this service, depending on where do you keep the tokens.
 * <p>
 * Default Implementation is {@link SessionAccessTokenExtractor},
 * which extracts the {@link AccessToken} from the {@link OAuthSession}.
 * 
	@author vladimir
 */
@UsesOrderedConfiguration(AccessTokenExtractor.class)
public interface AccessTokenExtractor {

	AccessToken get(OAuthClient client);
	
}
