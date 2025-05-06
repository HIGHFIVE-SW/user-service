package com.trendist.user_service.domain.user.service;

import org.springframework.stereotype.Service;

import com.trendist.user_service.domain.user.domain.User;
import com.trendist.user_service.domain.user.dto.request.UserFirstLoginSetupRequest;
import com.trendist.user_service.domain.user.dto.response.UserFirstLoginSetupResponse;
import com.trendist.user_service.domain.user.dto.response.UserProfileResponse;
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

	//사용자의 프로필을 가지고 오는 로직
	public UserProfileResponse getUserProfile(String email) {
		User user = getUser(email);
		return UserProfileResponse.from(user);
	}

	private User getUser(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
	}
}
