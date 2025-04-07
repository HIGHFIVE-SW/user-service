package com.trendist.user_service.global.response.code;

import com.trendist.user_service.global.response.dto.ErrorReasonDto;

public interface BaseErrorCode {

	public ErrorReasonDto getReason();

	public ErrorReasonDto getReasonHttpStatus();
}
