package com.trendist.user_service.domain.user.dto.response;

import java.util.Set;
import java.util.UUID;

import com.trendist.user_service.domain.user.domain.Keyword;
import com.trendist.user_service.domain.user.domain.User;

import lombok.Builder;

@Builder
public record UserFirstLoginSetupResponse(
	UUID id,
	String email,
	String username,
	String nickname,
	Set<Keyword> keywords
) {
	public static UserFirstLoginSetupResponse from(User user) {
		return UserFirstLoginSetupResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.username(user.getUsername())
			.nickname(user.getNickname())
			.keywords(user.getKeyword())
			.build();
	}
}
