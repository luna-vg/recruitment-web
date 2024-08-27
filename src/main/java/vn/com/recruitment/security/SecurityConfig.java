package vn.com.recruitment.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public UserDetailsManager userDetailsManager(DataSource dataSource) {
		
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		
		// define query to retrieve a user by username
		jdbcUserDetailsManager.setUsersByUsernameQuery(
				"select email, password, status from user where email=?");
		
		// define query to retrieve the authorities/roles by username
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"select user.email, role.role_name from user join role on user.role_id = role.id where email=?");
		
		return jdbcUserDetailsManager;
		
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(configurer ->
				configurer
						.requestMatchers("/assets/**").permitAll()
						.requestMatchers("/").permitAll()
						.requestMatchers("/register").permitAll()
						.anyRequest().authenticated()
			)
			.formLogin(form ->
					form
							.loginPage("/showMyLoginPage")
							.loginProcessingUrl("/authenticateTheUser")
							.defaultSuccessUrl("/home", true)
							.permitAll()
			)
			.logout(logout -> logout.permitAll()
			)
			.exceptionHandling(configurer ->
					configurer.accessDeniedPage("/access-denied"));
		
		return http.build();
		
	}
	
}
