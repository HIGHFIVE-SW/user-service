package com.trendist.user_service.domain.user.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.trendist.user_service.domain.tier.domain.Tier;
import com.trendist.user_service.domain.tier.repository.TierRepository;
import com.trendist.user_service.domain.user.domain.User;
import com.trendist.user_service.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RankingScheduler {
	private final UserRepository userRepository;
	private final TierRepository tierRepository;

	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void updateUserRanking() {
		List<Tier> sortedTiers = tierRepository.findAllByOrderByRequiredExpDesc();
		List<User> allUsers = userRepository.findAll();

		updateAllUserTiers(allUsers, sortedTiers);
		updateAllUserRankings(allUsers);

		userRepository.saveAll(allUsers);
	}

	private void updateAllUserTiers(List<User> allUsers, List<Tier> sortedTiers) {
		for (User user : allUsers) {
			Tier newTier = calculateTierByExp(user.getExp(), sortedTiers);
			user.setTier(newTier);
		}
	}

	private void updateAllUserRankings(List<User> allUsers) {
		Map<String, List<User>> userByTier = allUsers.stream()
			.collect(Collectors.groupingBy(user -> user.getTier().getTierName()));

		for(Map.Entry<String, List<User>> entry : userByTier.entrySet()) {
			List<User> users = entry.getValue();

			users.sort((u1, u2) -> Integer.compare(u2.getExp(), u1.getExp()));

			assignRankingByExp(users);
		}
	}

	private Tier calculateTierByExp(int exp, List<Tier> sortedTiers) {
		for (Tier tier : sortedTiers) {
			if (exp >= tier.getRequiredExp()) {
				return tier;
			}
		}
		return sortedTiers.get(sortedTiers.size() - 1);
	}

	private void assignRankingByExp(List<User> users) {
		int currentRanking = 1;
		int position = 1;
		Integer prevExp = null;

		for (User user : users) {
			Integer exp = user.getExp();

			if (exp == 0){
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
	}
}
