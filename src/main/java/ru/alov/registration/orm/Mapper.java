package ru.alov.registration.orm;

import org.springframework.stereotype.Component;
import ru.alov.registration.entities.Entity;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public interface Mapper<T extends Entity> {

    Optional<T> findById(Long id);

    Map<Long, T> getIdentityMap();

    Optional<T> findByString(String s);

    void update(List<T> entities);

    void update(T entity);

    void insert(List<T> entities);

    Optional<T> insert (T entity);

    void delete(List<T> entities);

    void delete(T entity);

}
