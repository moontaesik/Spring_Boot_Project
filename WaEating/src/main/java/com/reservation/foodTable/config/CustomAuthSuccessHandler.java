package com.reservation.foodTable.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();;


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub'

		Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
		for (GrantedAuthority role : roles) {

			if (role.getAuthority().equals("ROLE_USER")) {
				redirectStrategy.sendRedirect(request, response, "/");
			} else if (role.getAuthority().equals("ROLE_OWNER")) {
				redirectStrategy.sendRedirect(request, response, "/restaurant-page/graph");
			} else if (role.getAuthority().equals("ROLE_ADMIN")) {
				redirectStrategy.sendRedirect(request, response, "/manager");
			}
		}
	}

}
