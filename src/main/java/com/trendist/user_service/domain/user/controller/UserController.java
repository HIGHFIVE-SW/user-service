package com.trendist.user_service.domain.user.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trendist.user_service.domain.user.dto.request.UserFirstLoginSetupRequest;
import com.trendist.user_service.domain.user.dto.request.UserProfileUpdateRequest;
import com.trendist.user_service.domain.user.dto.request.UserUpdateExpRequest;
import com.trendist.user_service.domain.user.dto.response.RankingResponse;
import com.trendist.user_service.domain.user.dto.response.UserFirstLoginSetupResponse;
import com.trendist.user_service.domain.user.dto.response.UserProfileResponse;
import com.trendist.user_service.domain.user.dto.response.UserProfileUpdateResponse;
import com.trendist.user_service.domain.user.dto.response.UserUpdateExpResponse;
import com.trendist.user_service.domain.user.service.UserService;
import com.trendist.user_service.global.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@Operation(
		summary = "사용자 초기 정보 입력",
		description = "사용자가 최초 로그인 시 초기 정보를 입력합니다."
	)
	@PostMapping("/profile")
	public ApiResponse<UserFirstLoginSetupResponse> setUserProfile(
		@AuthenticationPrincipal String email,
		@RequestBody UserFirstLoginSetupRequest request) {
		return ApiResponse.onSuccess(userService.setUserProfile(email, request));
	}

	@Operation(
		summary = "사용자 프로필 수정",
		description = "사용자 프로필을 수정합니다."
	)
	@PatchMapping("/profile/update")
	public ApiResponse<UserProfileUpdateResponse> updateProfile(
		@AuthenticationPrincipal String email,
		@RequestBody UserProfileUpdateRequest userProfileUpdateRequest) {
		return ApiResponse.onSuccess(userService.updateProfile(email, userProfileUpdateRequest));
	}

	@Operation(
		summary = "본인 프로필 조회",
		description = "로그인한 본인의 프로필을 조회합니다."
	)
	@GetMapping("/profile")
	public ApiResponse<UserProfileResponse> getMyProfile(
		@AuthenticationPrincipal String email
	) {
		return ApiResponse.onSuccess(userService.getMyProfile(email));
	}

	@Operation(
		summary = "특정 사용자의 프로필 조회",
		description = "특정 사용자의 프로필을 조회합니다."
	)
	@GetMapping("/profile/{userId}")
	public ApiResponse<UserProfileResponse> getUserProfile(@PathVariable(name = "userId") UUID userId) {
		return ApiResponse.onSuccess(userService.getUserProfile(userId));
	}

	@Operation(
		summary = "티어별 랭킹 조회",
		description = "각 티어별로 사용자들의 랭킹을 조회합니다."
	)
	@GetMapping("/rankings/{tierName}")
	public ApiResponse<List<RankingResponse>> getRankingByTier(@PathVariable String tierName) {
		return ApiResponse.onSuccess(userService.getRankingByTier(tierName));
	}

	@Operation(
		summary = "본인 티어 랭킹 조회",
		description = "로그인한 본인의 티어 랭킹을 조회합니다."
	)
	@GetMapping("/rankings")
	public ApiResponse<List<RankingResponse>> getMyTierRanking(
		@AuthenticationPrincipal String email
	) {
		return ApiResponse.onSuccess(userService.getMyTierRanking(email));
	}

	@Operation(
		summary = "리뷰글을 작성한 특정 유저의 exp update",
		description = "리뷰글을 작성한 특정 유저의 exp를 더해줍니다."
	)
	@PostMapping("/{userId}/exp")
	public ApiResponse<UserUpdateExpResponse> addExp(
		@PathVariable UUID userId,
		@RequestBody UserUpdateExpRequest request
	){
		return ApiResponse.onSuccess(userService.updateExp(userId, request));
	}
}
