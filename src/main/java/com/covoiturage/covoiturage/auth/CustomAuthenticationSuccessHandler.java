package com.covoiturage.covoiturage.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.covoiturage.covoiturage.entity.User;
import com.covoiturage.covoiturage.service.Services;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	@Qualifier("userService")
	private Services userService;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication)
			throws IOException, ServletException {
		System.out.println("\n\nIn customAuthenticationSuccessHandler\n\n");
		String userName = authentication.getName();
		System.out.println("userName=" + userName);
		User theUser = userService.findByUserName(userName);
		HttpSession session = request.getSession();
		session.setAttribute("user", theUser);
		response.sendRedirect(request.getContextPath() + "/");
	}
}
