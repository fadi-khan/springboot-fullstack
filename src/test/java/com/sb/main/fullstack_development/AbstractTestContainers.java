package com.sb.main.fullstack_development;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
public abstract class AbstractTestContainers {


    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(
                        mysqlContainer.getJdbcUrl(),
                        mysqlContainer.getUsername(),
                        mysqlContainer.getPassword())
                .load();
        flyway.migrate();
        System.out.println();
    }

    @Container
    protected static final MySQLContainer<?> mysqlContainer
            = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("fahadkhan-dao-unit-test")
            .withUsername("fahadkhan")
            .withPassword("123456");




    @DynamicPropertySource
    private static void registerDatasourceProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    protected DataSource getDataSource() {
       return DataSourceBuilder.create()
                .driverClassName(mysqlContainer.getDriverClassName())
                .url(mysqlContainer.getJdbcUrl())
                .username(mysqlContainer.getUsername())
                .password(mysqlContainer.getPassword())
               .build();
    }

}
