package com.trendist.user_service.global.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.trendist.user_service.global.jwt.OAuth2AuthenticationSuccessHandler;
import com.trendist.user_service.global.jwt.filter.JwtAuthenticationFilter;
import com.trendist.user_service.global.jwt.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring()
			.requestMatchers(new AntPathRequestMatcher("/static/**"));
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.cors(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.requestMatchers(
					"/oauth2/**", "/login/oauth2/**", "/api/token", "/swagger-ui/**"
					, "/v3/api-docs/**", "/users/*/exp"
				).permitAll()
				.anyRequest().authenticated())
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(userInfo -> userInfo
					.userService(customOAuth2UserService))
				.successHandler(oAuth2AuthenticationSuccessHandler))
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of(
			"http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setExposedHeaders(Arrays.asList("Authorization", "Authorization-refresh"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
