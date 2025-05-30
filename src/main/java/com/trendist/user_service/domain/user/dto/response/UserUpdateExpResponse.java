package com.trendist.user_service.domain.user.dto.response;

import java.util.UUID;

import com.trendist.user_service.domain.user.domain.User;

import lombok.Builder;

@Builder
public record UserUpdateExpResponse(
	UUID id,
	int exp
) {
	public static UserUpdateExpResponse from(User user) {
		return UserUpdateExpResponse.builder()
			.id(user.getId())
			.exp(user.getExp())
			.build();
	}
}
