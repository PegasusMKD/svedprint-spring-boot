package com.svedprint.main.configs.oauth.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Simple User that also keep track of the primary key.
 */
public class UserWithId extends User {
	private static final long serialVersionUID = 1L;
	private final String id;

	public UserWithId(String username, String password,
					  Collection<? extends GrantedAuthority> authorities, String id) {
		super(username, password, true, true, true, true, authorities);
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
