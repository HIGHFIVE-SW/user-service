package com.trendist.user_service.domain.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trendist.user_service.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);

	Optional<User> findById(UUID userId);

	List<User> findByTier_TierNameOrderByExpDesc(String tierName);

	List<User> findAllByRankingGreaterThanOrderByRankingAsc(int ranking);

	List<User> findByTier_TierNameAndRankingGreaterThanOrderByRankingAsc(String tierName, int ranking);

}
