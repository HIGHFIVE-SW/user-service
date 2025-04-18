package com.trendist.user_service.global.jwt.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.trendist.user_service.domain.user.domain.User;
import com.trendist.user_service.global.jwt.dto.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenProvider {
	private final JwtProperties jwtProperties;

	public String generateToken(User user, Duration expiredAt) {
		Date now = new Date();
		return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
	}

	private String makeToken(Date expiry, User user) {
		Date now = new Date();
		Key key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));

		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setIssuer(jwtProperties.getIssuer())
			.setIssuedAt(now)
			.setExpiration(expiry)
			.setSubject(user.getEmail())
			.claim("id", user.getId())
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean validToken(String token) {
		Key key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));

		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);
		String email = claims.getSubject();//@AuthenticationPrincipal 사용을 위해 필수
		Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
		return new UsernamePasswordAuthenticationToken(email, token, authorities);
	}

	private Claims getClaims(String token) {
		Key key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));

		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}
