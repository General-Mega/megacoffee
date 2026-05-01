package com.megacoffee.infra;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class DirectLogin extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 3400154245471924094L;

	public DirectLogin(final String loginId, final Object details) {
		super(null);
		
		this.loginId = loginId;
		setDetails(details);
	}
	
	final private String loginId;

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return loginId;
	}
}
