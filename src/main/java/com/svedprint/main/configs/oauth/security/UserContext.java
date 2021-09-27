package com.svedprint.main.configs.oauth.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public final class UserContext {
	private static final String ANONYMOUS_USER = "anonymousUser";

	private UserContext() {
	}

	public static String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			Object principal = auth.getPrincipal();

			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			}

			return principal.toString();
		}

		return ANONYMOUS_USER;
	}


	public static boolean getAuthenticated() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			return auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
		}

		return false;
	}


	public static UserDetails getUserDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && auth.getPrincipal() instanceof UserDetails) {
			return ((UserDetails) auth.getPrincipal());
		}

		return null;
	}


	public static List<String> getRoles() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			return toStringList(auth.getAuthorities());
		}

		return emptyList();
	}

	public static boolean hasRole(String roleName) {
		for (String role : getRoles()) {
			if (role.equalsIgnoreCase(roleName)) {
				return true;
			}
		}

		return false;
	}

	public static List<String> toStringList(Iterable<? extends GrantedAuthority> grantedAuthorities) {
		List<String> result = new ArrayList<>();

		for (GrantedAuthority grantedAuthority : grantedAuthorities) {
			result.add(grantedAuthority.getAuthority());
		}

		return result;
	}
}
