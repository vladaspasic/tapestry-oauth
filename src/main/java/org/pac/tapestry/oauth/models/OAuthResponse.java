package org.pac.tapestry.oauth.models;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.internal.InternalConstants;
import org.apache.tapestry5.json.JSONObject;
import org.pac.tapestry.oauth.exceptions.OAuthResponseException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
	@author vladimir
 */

public class OAuthResponse {

protected final OAuthRequest request;
	
	private final Logger logger;
	
	private Map<String, String> headers;
	
	private InputStream inputStream;
	
	private int statusCode;
	
	private Response response;
	
	private JSONObject json;
	
	private Map<String, ApiFieldValue> map;
	
	private boolean completed;
	
	public OAuthResponse(OAuthRequest request) {	
		this.request = request;
		this.logger = LoggerFactory.getLogger(getClass());
	}
	
	public ContentType getContentType() {		
		String type = response.getHeader(InternalConstants.CONTENT_TYPE_ATTRIBUTE_NAME);

		return new ContentType(type);
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	public Map<String, String> getResponseHeaders() {
		return this.headers;
	}
	
	public String getResponseHeader(String name) {
		return this.headers.get(name);
	}
	
	public String asString() {
		if(!completed)
			execute();
			
		if(response == null)
			return "{}";
			
		return response.getBody();
	}
	
	public JSONObject asJSON() {
		if(json == null)
			json = new JSONObject(asString());
		
		return json;
	}
	
	public Map<String, ApiFieldValue> asMap() {
		if(map == null) {
			
			map = new HashMap<String, ApiFieldValue>();
			
			for (String key : asJSON().keys()) {
				Object value = asJSON().get(key);
				map.put(key, new ApiFieldValue(value));
			}
			
		}
		
		return map;
	}
	
	public ApiFieldValue getValue(String key) {
		
		return new ApiFieldValue(asMap().get(key));
	}
	
	public int getStatus() {
		return statusCode;
	}
	
	private void execute() {
		
		logger.info("Creating request to {} {}",request.getVerb(), request.getCompleteUrl());
		
		response = request.send();
		
		headers = response.getHeaders();
		
		inputStream = response.getStream();
		
		statusCode = response.getCode();
		
		logger.info("Request responded with {} {}", statusCode, response.getMessage());
		
		completed = true;
		
		if(statusCode >= 300) {
			
			logger.error(response.getBody());
			
			throw new OAuthResponseException(response);
		}
		
	}

	@Override
	public String toString() {
		return "OAuthResponse [URL=" + request.getCompleteUrl() + ", statusCode="
				+ statusCode + ", response=" + asString() + "]";
	}
	
}
