package com.trendist.user_service.domain.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trendist.user_service.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);

	Optional<User> findById(UUID userId);

	List<User> findAllByOrderByExpDesc();

	List<User> findAllByRankingGreaterThanOrderByRankingAsc(int ranking);
}
