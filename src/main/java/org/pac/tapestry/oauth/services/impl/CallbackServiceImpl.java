package org.pac.tapestry.oauth.services.impl;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;
import org.pac.tapestry.oauth.OAuthSymbolConstants;
import org.pac.tapestry.oauth.OAuthTapestryConstants;
import org.pac.tapestry.oauth.services.CallbackService;

/**
	@author vladimir
 */

public class CallbackServiceImpl implements CallbackService {

	@Inject
	private PageRenderLinkSource pageRenderLinkSource;
	
	@Inject
	@Symbol(OAuthSymbolConstants.OAUTH_CALLBACK_PATH)
	private String callbackPath;
	
	@Inject
	private Request request;
	
	@Override
	public String create(String client) {
		
		final String context = request.getContextPath();
		
		final Link link = pageRenderLinkSource.createPageRenderLink("");
		
		link.addParameter(OAuthTapestryConstants.OAUTH_CLIENT_ID_PARAMETER, client);
		
		Link callback = link.copyWithBasePath(context + "/" + callbackPath);
		
		return callback.toAbsoluteURI();
	}
	
}
