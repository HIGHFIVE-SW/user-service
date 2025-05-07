package com.trendist.user_service.domain.user.dto.response;

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
	Keyword keyword,
	String profileUrl,
	int exp,
	String tierName,
	String tierImageUrl,
	int ranking
) {
	public static UserProfileResponse from(User user) {
		return UserProfileResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.email(user.getEmail())
			.nickname(user.getNickname())
			.keyword(user.getKeyword())
			.profileUrl(user.getProfileUrl())
			.exp(user.getExp())
			.tierName(user.getTier().getTierName())
			.tierImageUrl(user.getTier().getTierImageUrl())
			.ranking(user.getRanking())
			.build();
	}
}
