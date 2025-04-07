package com.trendist.user_service.global.response.code;

import com.trendist.user_service.global.response.dto.ReasonDto;

public interface BaseCode {

	public ReasonDto getReason();

	public ReasonDto getReasonHttpStatus();
}
