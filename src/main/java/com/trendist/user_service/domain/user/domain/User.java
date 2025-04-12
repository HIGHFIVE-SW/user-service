package com.trendist.user_service.domain.user.domain;

import java.util.UUID;

import com.trendist.user_service.global.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
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

	/*@Column(name = "nickname")
	private String nickname;

	@Enumerated(EnumType.STRING)
	@Column(name = "keyword")
	private Keyword keyword;

	@Column(name = "exp")
	@Builder.Default
	private Integer exp = 0;

	@Column(name="ranking")
	private Integer ranking;*/
}
