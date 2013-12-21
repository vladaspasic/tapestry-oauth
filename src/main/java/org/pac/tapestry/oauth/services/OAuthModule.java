package org.pac.tapestry.oauth.services;

import java.util.List;
import java.util.Map;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Primary;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.ioc.services.StrategyBuilder;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.pac.tapestry.oauth.OAuthSymbolConstants;
import org.pac.tapestry.oauth.clients.OAuthClient;
import org.pac.tapestry.oauth.services.impl.CallbackDispatcher;
import org.pac.tapestry.oauth.services.impl.CallbackServiceImpl;
import org.pac.tapestry.oauth.services.impl.ClientValueEncoder;
import org.pac.tapestry.oauth.services.impl.DefaultOAuthHTTPOperations;
import org.pac.tapestry.oauth.services.impl.OAuth10ServiceAdapter;
import org.pac.tapestry.oauth.services.impl.OAuth20ServiceAdapter;
import org.pac.tapestry.oauth.services.impl.OAuthClientProviderImpl;
import org.pac.tapestry.oauth.services.impl.SessionAccessTokenExtractor;

public class OAuthModule {
	
	public static void bind(ServiceBinder binder)
    {
		binder.bind(CallbackService.class, CallbackServiceImpl.class);
		binder.bind(OAuthHTTPOperations.class, DefaultOAuthHTTPOperations.class);
		binder.bind(OAuthClientProvider.class, OAuthClientProviderImpl.class);
    }
	
	public static OAuthServiceAdapter buildOAuthServiceAdapter(final ChainBuilder builder, final List<OAuthServiceAdapter> chains) {
		return builder.build(OAuthServiceAdapter.class, chains);
	}
	
	@Contribute(OAuthServiceAdapter.class)
	public static void contributeOAuthServiceAdapter(final OrderedConfiguration<OAuthServiceAdapter> configuration) {
		configuration.addInstance("1.0", OAuth10ServiceAdapter.class, "before:*");
		configuration.addInstance("2.0", OAuth20ServiceAdapter.class, "after:1.0");
	}
	
	public static AccessTokenExtractor buildAccessTokenExtractor(final ChainBuilder builder, final List<AccessTokenExtractor> chains) {
		return builder.build(AccessTokenExtractor.class, chains);
	}
	
	@Contribute(AccessTokenExtractor.class)
	public static void contributeAccessTokenExtractor(final OrderedConfiguration<AccessTokenExtractor> configuration) {
		configuration.addInstance("Session", SessionAccessTokenExtractor.class, "before:*");
	}
	
	public static AuthenticationSuccessHandler buildAuthenticationSuccessHandler(final StrategyBuilder builder, final Map<Class, AuthenticationSuccessHandler> config) {
		return builder.build(AuthenticationSuccessHandler.class, config);
	}
	
	/**
	 * Important !
	 * 
	 * Do not use addInstance in the contribution, as the Proxy for this class will be created by the T5 IoC,
	 * and {@link AuthenticationSuccessHandler} contributions will have no effect.
	 * <p>
	 * Please Create new Instances, as this is just a simple configuration Object.
	 */
	@Contribute(OAuthClientProvider.class)
	public static void contributeOAuthClientProvider(final Configuration<OAuthClient> configuration, CallbackService callbackService) {
	
	}
	
	@Contribute(Dispatcher.class)
	@Primary
	public static void provideOAuthDispatchers(final OrderedConfiguration<Dispatcher> configuration) {
		configuration.addInstance("OAuthCallback", CallbackDispatcher.class, "before:*");
	}
	
	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void provideApplicationDefaults(MappedConfiguration<String, String> configuration){
		
		configuration.add(OAuthSymbolConstants.OAUTH_CALLBACK_PATH, "oauth/callback");
		
	}
	
	@Contribute(ValueEncoderSource.class)
	public static void provideEncoder(MappedConfiguration<Class, ValueEncoder> configuration){
		configuration.addInstance(OAuthClient.class, ClientValueEncoder.class);
	}
	
	@Contribute(ComponentClassResolver.class)
	public static void contributeComponentClassResolver(final Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("oauth", "org.pac.tapestry.oauth"));
	}
	
}
