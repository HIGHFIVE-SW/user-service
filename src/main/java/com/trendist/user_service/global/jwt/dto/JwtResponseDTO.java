package com.trendist.user_service.global.jwt.dto;

import java.util.UUID;

import com.trendist.user_service.domain.user.domain.User;

import lombok.Builder;

@Builder
public record JwtResponseDTO(
	UUID userId,
	String username,
	String email,
	String accessToken
) {
	public static JwtResponseDTO from(User user, String accessToken) {
		return JwtResponseDTO.builder()
			.userId(user.getId())
			.username(user.getUsername())
			.email(user.getEmail())
			.accessToken(accessToken)
			.build();
	}
}
