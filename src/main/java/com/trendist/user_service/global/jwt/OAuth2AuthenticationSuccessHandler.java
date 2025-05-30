package com.trendist.user_service.global.jwt;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.trendist.user_service.domain.user.domain.User;
import com.trendist.user_service.domain.user.repository.UserRepository;
import com.trendist.user_service.global.exception.ApiException;
import com.trendist.user_service.global.jwt.provider.TokenProvider;
import com.trendist.user_service.global.response.status.ErrorStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final UserRepository userRepository;
	private final TokenProvider tokenProvider;

	@Value("${app.frontend.redirect-uri}")
	private String frontendUri;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		String email = oAuth2User.getAttribute("email");

		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));

		String accessToken = tokenProvider.generateToken(user, Duration.ofHours(2));

		String redirectUrl = UriComponentsBuilder
			.fromUriString(frontendUri)
			.queryParam("token", accessToken)
			.build()
			.toUriString();

		response.sendRedirect(redirectUrl);
	}
}
