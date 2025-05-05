package com.trendist.user_service.domain.user.dto.request;

import java.util.Set;

import com.trendist.user_service.domain.user.domain.Keyword;

import lombok.Builder;

@Builder
public record UserFirstLoginSetupRequest(
	String nickname,
	Keyword keyword,
	String profileUrl
) {

}
