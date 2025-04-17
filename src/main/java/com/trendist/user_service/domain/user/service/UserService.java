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

	public UserFirstLoginSetupResponse setUserProfile(String email,
		UserFirstLoginSetupRequest userFirstLoginSetupRequest) {
		User user = getUser(email);

		if (user.getIsJoined()) {
			throw new ApiException(ErrorStatus._USER_NOT_FIRST_LOGIN);
		}

		user.setNickname(userFirstLoginSetupRequest.nickname());
		user.setKeywords(userFirstLoginSetupRequest.keywords());
		user.setIsJoined(true);

		return UserFirstLoginSetupResponse.from(userRepository.save(user));
	}

	public UserProfileResponse getUserProfile(String email) {
		User user = getUser(email);
		return UserProfileResponse.from(user);
	}

	private User getUser(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new ApiException(ErrorStatus._USER_NOT_FOUND));
	}
}
