package com.app.ebook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.app.ebook.model.Role.*;

import com.app.ebook.security.JwtAuthFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtAuthFilter jwtFilter;
	private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers(MyConstants.PUBLIC_URLS)
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, MyConstants.PUBLIC_GET_URLS)
                    .permitAll()
                    .requestMatchers(MyConstants.ADMIN_URLS).hasRole(ADMIN.name())
                    .anyRequest()
                    .authenticated())
            .exceptionHandling(handling -> handling
                    .authenticationEntryPoint(
                            (request, response, authException) ->
                                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")))
            .sessionManagement(management -> management
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
    	return http.build();
	}

}
