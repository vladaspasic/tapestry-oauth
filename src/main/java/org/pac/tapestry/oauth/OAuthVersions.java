package org.pac.tapestry.oauth;

/**
	@author vladimir
 */

public enum OAuthVersions {

	V1_0("1.0"), V2_0("2.0");
	
	private String version;
	
	private OAuthVersions(String version) {
		this.version = version;
	}
	
	public String getVersion() {
		return version;
	}
	
	public boolean isMatchingVersion(String version) {
		return this.version.equalsIgnoreCase(version);
	}
	
}
