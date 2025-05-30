package com.trendist.user_service.domain.user.dto.request;

import lombok.Builder;

@Builder
public record UserUpdateExpRequest(
	int exp
) {
}
