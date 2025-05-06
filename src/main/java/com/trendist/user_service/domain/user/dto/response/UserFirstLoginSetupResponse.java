package com.trendist.user_service.domain.user.dto.response;

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
	Keyword keyword,
	String profileUrl,
	Boolean isJoined
) {
	public static UserFirstLoginSetupResponse from(User user) {
		return UserFirstLoginSetupResponse.builder()
			.id(user.getId())
			.email(user.getEmail())
			.username(user.getUsername())
			.nickname(user.getNickname())
			.keyword(user.getKeyword())
			.profileUrl(user.getProfileUrl())
			.isJoined(user.getIsJoined())
			.build();
	}
}
