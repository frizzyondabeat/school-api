package com.example.entities.containers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public class MySqlTestContainer {
    static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("testrun")
            .withUsername("root")
            .withPassword("tomriddle");

    @DynamicPropertySource
    public static void setMySQLContainer(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    static {
        mySQLContainer.start();
    }
}
