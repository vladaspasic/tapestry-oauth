package org.pac.tapestry.oauth.components;

import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.services.OAuthClientProvider;

/**
	@author vladimir
 */
@SupportsInformalParameters
public class Logins {
	
	@Parameter(required = true, defaultPrefix=BindingConstants.PROP)
	@Property
	private OAuthClient client;

	@Inject
	private OAuthClientProvider authClientProvider;
	
	@Property
	List<OAuthClient> supportedClients;
	
	public void SetupRender() {		
		supportedClients = authClientProvider.getSupportedClients();
	}
	
}
