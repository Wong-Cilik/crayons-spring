package com.crayons_2_0.authentication;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.crayons_2_0.MyUI;
import com.crayons_2_0.service.Language;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class AuthManager implements AuthenticationManager {

	@Autowired
	private UserService userService;
	private static boolean hasAuthority = false;

	public Authentication authenticate(Authentication auth)
			throws AuthenticationException, UsernameNotFoundException {
		String username = (String) auth.getPrincipal();
		String password = (String) auth.getCredentials();

		UserDetails user = userService.loadUserByUsername(username);

		if (user != null && user.getPassword().equals(password)) {
			Collection<? extends GrantedAuthority> authorities = user
					.getAuthorities();
			hasAuthority = true;
			if (userService.findByEMail(username).getLanguage() == Language.English) {
	            LanguageService.getInstance().setCurrentLocale(Language.English);
	        } else if (userService.findByEMail(username).getLanguage() == Language.German) {
	            LanguageService.getInstance().setCurrentLocale(Language.German);
	        }
			MyUI.get().showMainView();
			return new UsernamePasswordAuthenticationToken(username, password,
					authorities);
		}
		throw new BadCredentialsException("Bad Credentials");
	}

	public static void setHasAuthority(boolean hasAuthority) {
		AuthManager.hasAuthority = hasAuthority;
	}

	public static boolean isHasAuthority() {
		return hasAuthority;
	}
}
