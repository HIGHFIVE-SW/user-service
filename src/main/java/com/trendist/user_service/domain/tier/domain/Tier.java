package com.trendist.user_service.domain.tier.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tier")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tier {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "tier_id")
	private UUID id;

	@Column(name = "tier_name")
	private String tierName;

	@Column(name = "required_exp")
	private Integer requiredExp;

	@Column(name = "tier_image_url")
	private String tierImageUrl;

}
