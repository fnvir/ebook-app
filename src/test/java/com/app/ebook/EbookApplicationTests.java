package com.app.ebook;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = DatabasePropertiesInitializer.class)
class EbookApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(EbookApplicationTests.class);

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Test
    void contextLoads(TestInfo testInfo) {
        logger.info("Running test: {}", testInfo.getDisplayName());
        logger.info("Datasource URL: {}", datasourceUrl);
        logger.info("Datasource Username: {}", datasourceUsername);
        logger.info("Datasource Password: {}", datasourcePassword);  // Be cautious with logging passwords!
    }
}

class DatabasePropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Environment env = applicationContext.getEnvironment();

        // Fetch the database properties from the environment
        String dbUrl = env.getProperty("spring.datasource.url");
        String dbUsername = env.getProperty("spring.datasource.username");
        String dbPassword = env.getProperty("spring.datasource.password");

        // Print the properties
        System.out.println("===== Database Properties Before Tests =====");
        System.out.println("DB URL: " + dbUrl);
        System.out.println("DB Username: " + dbUsername);
        System.out.println("DB Password: " + dbPassword);  // Caution: do not print passwords in production logs
        System.out.println("===========================================");
    }
}

