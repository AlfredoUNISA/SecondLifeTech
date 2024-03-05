package it.unisa.is.secondlifetech.config;

import it.unisa.is.secondlifetech.entity.constant.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private UserDetailsService userDetailsService;

	@Autowired
	public WebSecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) ->
				csrf.disable() // disabilitato perché dà alcuni problemi di CSRF token
			).authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/").permitAll()
				.requestMatchers("/cliente/**").hasAnyAuthority(UserRole.CLIENTE)
				.requestMatchers("/gestore/**").hasAnyAuthority(UserRole.GESTORE_PRODOTTI, UserRole.GESTORE_UTENTI, UserRole.GESTORE_ORDINI)
				.requestMatchers("/dashboard-prodotti/**").hasAnyAuthority(UserRole.GESTORE_PRODOTTI)
				.requestMatchers("/dashboard-ordini/**").hasAnyAuthority(UserRole.GESTORE_ORDINI)
				.requestMatchers("/dashboard-utenti/**").hasAnyAuthority(UserRole.GESTORE_UTENTI)
				.anyRequest().permitAll()
			).rememberMe((remember) -> remember
				.rememberMeServices(rememberMeServices(userDetailsService))
			).formLogin((form) -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/")
				.permitAll()
			).logout((logout) -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/")
				.permitAll()
			);
		return http.build();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	private static final int MINUTE = 60;
	private static final int HOUR = 60 * MINUTE;
	private static final int DAY = 24 * HOUR;
	private static final int WEEK = 7 * DAY;
	public static final int COOKIE_MAX_AGE = 30;

	@Bean
	public RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
		TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(
			"myKey",
			userDetailsService,
			TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256
		);
		rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
		rememberMe.setTokenValiditySeconds(COOKIE_MAX_AGE);
		return rememberMe;
	}
}
