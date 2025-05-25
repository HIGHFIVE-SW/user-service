package com.trendist.user_service.domain.user.domain;

import java.util.UUID;

import com.trendist.user_service.domain.tier.domain.Tier;
import com.trendist.user_service.global.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private UUID id;

	@Column(name = "username")
	private String username;

	@Column(name = "email")
	private String email;

	@Column(name = "nickname")
	private String nickname;

	@Enumerated(EnumType.STRING)
	@Column(name = "keyword")
	private Keyword keyword;

	@Column(name = "profile_url")
	@Builder.Default
	String profileUrl = "기본값";

	@Column(name = "is_joined")
	@Builder.Default
	private Boolean isJoined = false;

	@Column(name = "exp")
	@Builder.Default
	private Integer exp = 0;

	@ManyToOne
	@JoinColumn(name = "tier_id")
	private Tier tier;

	@Column(name = "ranking")
	@Builder.Default
	private Integer ranking = 0;
}
