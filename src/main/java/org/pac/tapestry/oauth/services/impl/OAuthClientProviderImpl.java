package org.pac.tapestry.oauth.services.impl;

import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.func.F;
import org.apache.tapestry5.func.Predicate;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.services.OAuthClientProvider;

/**
	@author vladimir
 */

public class OAuthClientProviderImpl implements OAuthClientProvider {

	@Inject
	private Collection<OAuthClient> config;
	
	@Override
	public OAuthClient get(final Class clientClass) {
		
		return F.flow(config).filter(new Predicate<OAuthClient>() {
			
			public boolean accept(OAuthClient client) {
				return client.getClass().equals(clientClass);
			};
			
		}).first();
	}
	
	@Override
	public OAuthClient get(final String clientId) {
		
		return F.flow(config).filter(new Predicate<OAuthClient>() {
			
			public boolean accept(OAuthClient client) {
				return client.getClientId().equals(clientId);
			};
			
		}).first();
	}
	
	@Override
	public List<OAuthClient> getSupportedClients() {
		
		return CollectionFactory.newList(config);
	}
	
}
