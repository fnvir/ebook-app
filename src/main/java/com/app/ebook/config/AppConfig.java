package com.app.ebook.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.ebook.repository.UserRepository;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@RequiredArgsConstructor
@Slf4j
public class AppConfig {

    @Value("${app.initialize-tempdata:false}")
    private boolean initializeData;
    
	private final UserRepository repository;

	@Bean
	UserDetailsService userDetailsService() {
		return identifier -> repository.findByEmailOrUsername(identifier)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

    @Bean
    com.fasterxml.jackson.databind.Module hibernateModule() {
        return new Hibernate5JakartaModule();
    }
    
    @Bean
    DataSourceInitializer dataSourceInitializer(@Qualifier("dataSource") final DataSource dataSource) {
        if(!initializeData || repository.count()>0)
            return null;
        log.info("Initializing db with temp data");
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/data.sql"));
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }
    
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .version("0.9.9")
                        .title("Ebook Sharing App - API")
                        .description("Backend API for Ebook Sharing App - A platform where users can upload pdfs and share with others. Made with Spring Boot")
                        .contact(new Contact().name("Farhan Tanvir").url("https://linkedin.com/in/fnvir").email("farhantaanvir@gmail.com")))
//                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

}
