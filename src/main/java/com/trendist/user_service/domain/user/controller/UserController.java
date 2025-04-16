package com.trendist.user_service.domain.user.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trendist.user_service.domain.user.dto.request.UserFirstLoginSetupRequest;
import com.trendist.user_service.domain.user.dto.response.UserFirstLoginSetupResponse;
import com.trendist.user_service.domain.user.service.UserService;
import com.trendist.user_service.global.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@PostMapping("/profile")
	public ApiResponse<UserFirstLoginSetupResponse> setUserProfile(
		@AuthenticationPrincipal String email,
		@RequestBody UserFirstLoginSetupRequest request){
		return ApiResponse.onSuccess(userService.setUserProfile(email,request));
	}
}
