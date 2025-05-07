package com.trendist.user_service.domain.user.dto.response;

import java.util.UUID;

import com.trendist.user_service.domain.user.domain.User;

import lombok.Builder;

@Builder
public record RankingResponse(
	int ranking,
	UUID id,
	String nickname,
	String profileUrl,
	int exp
) {
	public static RankingResponse from(User user) {
		return RankingResponse.builder()
			.ranking(user.getRanking())
			.id(user.getId())
			.nickname(user.getNickname())
			.profileUrl(user.getProfileUrl())
			.exp(user.getExp())
			.build();
	}
}
