package com.trendist.user_service.domain.user.dto.response;

import java.util.Set;
import java.util.UUID;

import com.trendist.user_service.domain.user.domain.Keyword;
import com.trendist.user_service.domain.user.domain.User;

import lombok.Builder;

@Builder
public record UserProfileResponse(
	UUID id,
	String username,
	String email,
	String nickname,
	Set<Keyword> keywords,
	int exp,
	String tierName,
	String tierImageUrl
) {
	public static UserProfileResponse from(User user) {
		return UserProfileResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.keywords(user.getKeywords())
			.exp(user.getExp())
			.tierName(user.getTier().getTierName())
			.tierImageUrl(user.getTier().getTierImageUrl())
			.build();
	}
}
