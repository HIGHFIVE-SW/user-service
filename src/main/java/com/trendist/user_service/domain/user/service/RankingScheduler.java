package com.trendist.user_service.domain.user.service;

import java.util.List;

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
		updateAllUserTiers();

		List<Tier> tiers = tierRepository.findAll();
		for (Tier tier : tiers) {
			updateUserByTier(tier);
		}
	}

	private void updateAllUserTiers() {
		List<Tier> sortedTiers = tierRepository.findAllByOrderByRequiredExpDesc();
		List<User> allUsers = userRepository.findAll();

		for (User user : allUsers) {
			Tier newTier = calculateTierByExp(user.getExp(), sortedTiers);
			user.setTier(newTier);
		}

		userRepository.saveAll(allUsers);
	}

	private Tier calculateTierByExp(int exp, List<Tier> sortedTiers) {
		for (Tier tier : sortedTiers) {
			if (exp >= tier.getRequiredExp()) {
				return tier;
			}
		}
		return sortedTiers.get(sortedTiers.size() - 1);
	}

	private void updateUserByTier(Tier tier) {
		List<User> users = userRepository.findByTier_TierNameOrderByExpDesc(tier.getTierName());

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
