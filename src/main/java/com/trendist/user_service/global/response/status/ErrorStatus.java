package com.trendist.user_service.global.response.status;

import org.springframework.http.HttpStatus;

import com.trendist.user_service.global.response.code.BaseErrorCode;
import com.trendist.user_service.global.response.dto.ErrorReasonDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//발생할 수 있는 다양한 오류 상황에 대해 미리 정의된 상수들을 모아두었습니다.
//HTTP 상태, 코드, 메시지를 같이 가지고 있습니다.
@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	_JWT_NOT_FOUND(HttpStatus.NOT_FOUND, "JWT_001", "Header에 JWT가 존재하지 않습니다."),
	_GITHUB_TOKEN_GENERATION_ERROR(HttpStatus.NOT_FOUND, "JWT_002", "깃허브에서 엑세스 토큰을 발급하는 과정에서 오류가 발생했습니다."),

	_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "해당 유저가 존재하지 않습니다"),
	_USER_NOT_FIRST_LOGIN(HttpStatus.BAD_REQUEST, "USER_002", "최초 로그인한 유저가 아닙니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ErrorReasonDto getReason() {
		return ErrorReasonDto.builder()
			.isSuccess(false)
			.code(code)
			.message(message)
			.build();
	}

	@Override
	public ErrorReasonDto getReasonHttpStatus() {
		return ErrorReasonDto.builder()
			.httpStatus(httpStatus)
			.isSuccess(false)
			.code(code)
			.message(message)
			.build();
	}
}
