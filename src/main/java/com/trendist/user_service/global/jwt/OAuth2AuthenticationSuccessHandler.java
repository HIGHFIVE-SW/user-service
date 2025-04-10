package com.trendist.user_service.global.jwt;

import java.io.IOException;
import java.time.Duration;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendist.user_service.domain.user.domain.User;
import com.trendist.user_service.domain.user.repository.UserRepository;
import com.trendist.user_service.global.exception.ApiException;
import com.trendist.user_service.global.jwt.dto.JwtResponseDTO;
import com.trendist.user_service.global.jwt.provider.TokenProvider;
import com.trendist.user_service.global.response.ApiResponse;
import com.trendist.user_service.global.response.status.ErrorStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final UserRepository userRepository;
	private final TokenProvider tokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		String email = oAuth2User.getAttribute("email");

		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));

		String accessToken = tokenProvider.generateToken(user, Duration.ofHours(2));

		response.setContentType("application/json;charset=UTF-8"); // UTF-8 인코딩 명시
		response.setCharacterEncoding("UTF-8"); // 추가적으로 문자 인코딩 설정

		ApiResponse<JwtResponseDTO> apiResponse = ApiResponse.onSuccess(
			new JwtResponseDTO(user.getId(), user.getUsername(), accessToken)
		);

		new ObjectMapper().writeValue(response.getWriter(), apiResponse);
	}
}
