package com.trendist.user_service.domain.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.trendist.user_service.domain.tier.domain.Tier;
import com.trendist.user_service.domain.user.domain.User;
import com.trendist.user_service.domain.user.dto.request.UserFirstLoginSetupRequest;
import com.trendist.user_service.domain.user.dto.request.UserProfileUpdateRequest;
import com.trendist.user_service.domain.user.dto.response.RankingResponse;
import com.trendist.user_service.domain.user.dto.response.UserFirstLoginSetupResponse;
import com.trendist.user_service.domain.user.dto.response.UserProfileResponse;
import com.trendist.user_service.domain.user.dto.response.UserProfileUpdateResponse;
import com.trendist.user_service.domain.user.repository.UserRepository;
import com.trendist.user_service.global.exception.ApiException;
import com.trendist.user_service.global.response.status.ErrorStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;

	// 처음 로그인한 사용자의 경우 초기 정보 입력하는 로직
	public UserFirstLoginSetupResponse setUserProfile(String email,
		UserFirstLoginSetupRequest userFirstLoginSetupRequest) {
		User user = getUser(email);

		if (user.getIsJoined()) {
			throw new ApiException(ErrorStatus._USER_NOT_FIRST_LOGIN);
		}

		user.setNickname(userFirstLoginSetupRequest.nickname());
		user.setKeyword(userFirstLoginSetupRequest.keyword());
		user.setIsJoined(true);

		if (userFirstLoginSetupRequest.profileUrl() != null
			&& !userFirstLoginSetupRequest.profileUrl().isBlank()) {
			user.setProfileUrl(userFirstLoginSetupRequest.profileUrl());
		}

		return UserFirstLoginSetupResponse.from(userRepository.save(user));
	}

	public UserProfileUpdateResponse updateProfile(String email,
		UserProfileUpdateRequest userProfileUpdateRequest) {
		User user = getUser(email);

		//기본값으로 원래 설정되어있던 값들이 설정되어있기에 전부 바꿔야함.
		user.setNickname(userProfileUpdateRequest.nickname());
		user.setKeyword(userProfileUpdateRequest.keyword());
		user.setProfileUrl(userProfileUpdateRequest.profileUrl());

		return UserProfileUpdateResponse.from(userRepository.save(user));
	}

	//본인의 프로필을 가지고 오는 로직
	public UserProfileResponse getMyProfile(String email) {
		User user = getUser(email);
		return UserProfileResponse.from(user);
	}

	//특정 사용자의 프로필을 가지고 오는 로직
	public UserProfileResponse getUserProfile(UUID userId) {
		User user = getUser(userId);
		return UserProfileResponse.from(user);
	}

	public List<RankingResponse> getRankingByTier(String tierName) {
		return userRepository.findByTier_TierNameOrderByRankingAsc(tierName)
			.stream()
			.filter(user -> user.getRanking() > 0)
			.map(RankingResponse::from)
			.toList();
	}

	public List<RankingResponse> getMyTierRanking(String email) {
		User currentUser = getUser(email);
		Tier userTier = currentUser.getTier();
		String tierName = userTier.getTierName();
		return getRankingByTier(tierName);
	}

	private User getUser(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
	}

	private User getUser(UUID userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
	}
}
