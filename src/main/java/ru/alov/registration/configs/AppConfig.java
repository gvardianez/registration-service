package ru.alov.registration.configs;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Configuration
public class AppConfig {

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database.user}")
    private String databaseUser;

    @Value("${database.password}")
    private String databasePassword;

    @Bean
    public Flyway flyway(){
        return Flyway.configure().dataSource(databaseUrl,databaseUser,databasePassword).load();
    }

    @Bean
    public Connection connection() {
        try {
            return DriverManager.getConnection(databaseUrl, databaseUser,databasePassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException();
        }
    }
}
