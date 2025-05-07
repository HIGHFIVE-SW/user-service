package com.trendist.user_service.domain.user.dto.response;

import java.util.UUID;

import com.trendist.user_service.domain.user.domain.Keyword;
import com.trendist.user_service.domain.user.domain.User;

import lombok.Builder;

@Builder
public record UserProfileUpdateResponse(
	UUID id,
	String nickname,
	Keyword keyword,
	String profileUrl
) {
	public static UserProfileUpdateResponse from(User user) {
		return UserProfileUpdateResponse.builder()
			.id(user.getId())
			.nickname(user.getNickname())
			.keyword(user.getKeyword())
			.profileUrl(user.getProfileUrl())
			.build();
	}
}
