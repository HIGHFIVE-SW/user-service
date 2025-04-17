package com.trendist.user_service.global.jwt.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.trendist.user_service.domain.tier.domain.Tier;
import com.trendist.user_service.domain.tier.repository.TierRepository;
import com.trendist.user_service.domain.user.domain.User;
import com.trendist.user_service.domain.user.repository.UserRepository;
import com.trendist.user_service.global.exception.ApiException;
import com.trendist.user_service.global.response.status.ErrorStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final UserRepository userRepository;
	private final TierRepository tierRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = oauth.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		Map<String, Object> attributes = oAuth2User.getAttributes();
		String email = (String)attributes.get("email");
		String username = (String)attributes.get("name");

		User user = userRepository.findByEmail(email)
			.orElseGet(() -> {
				Tier tier = tierRepository.findByTierName("초보 여행자")
					.orElseThrow(() -> new ApiException(ErrorStatus._TIER_NOT_FOUND));

				User newUser = User.builder()
					.email(email)
					.username(username)
					.isJoined(false)
					.tier(tier)
					.build();
				return userRepository.save(newUser);
			});
		return new DefaultOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
			attributes,
			userNameAttributeName);
	}
}
