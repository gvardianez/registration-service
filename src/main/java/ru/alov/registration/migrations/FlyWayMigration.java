package ru.alov.registration.migrations;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class FlyWayMigration {

    private final Flyway flyway;

    @PostConstruct()
    public void migrate(){
        flyway.migrate();
    };

}
