package com.trendist.user_service.domain.tier.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trendist.user_service.domain.tier.domain.Tier;

public interface TierRepository extends JpaRepository<Tier, UUID> {
	Optional<Tier> findByTierName(String tierName);

	List<Tier> findAllByOrderByRequiredExpDesc();

}
