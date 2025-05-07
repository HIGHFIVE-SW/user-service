package com.trendist.user_service.domain.user.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.trendist.user_service.domain.user.domain.User;
import com.trendist.user_service.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RankingScheduler {
	private final UserRepository userRepository;

	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void updateUserRanking() {
		List<User> users = userRepository.findAllByOrderByExpDesc();

		int currentRanking = 1;
		int position = 1;
		Integer prevExp = null;

		for (User user : users) {
			Integer exp = user.getExp();

			if (exp == 0) {
				user.setRanking(0);
				position++;
				continue;
			}

			if (!exp.equals(prevExp)) {
				currentRanking = position;
			}

			user.setRanking(currentRanking);
			prevExp = exp;
			position++;
		}
		userRepository.saveAll(users);
	}
}
