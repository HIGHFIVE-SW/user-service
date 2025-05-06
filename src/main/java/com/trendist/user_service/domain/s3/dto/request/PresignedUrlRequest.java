package com.trendist.user_service.domain.s3.dto.request;

public record PresignedUrlRequest(
	String imageName
) {
}
