package ru.alov.registration.repositories;

import org.springframework.stereotype.Component;
import ru.alov.registration.entities.Entity;
import ru.alov.registration.orm.Mapper;
import ru.alov.registration.orm.UnitOfWork;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class Repository<T extends Entity> {

    private final Connection conn;

    private final Mapper<T> mapper;

    private final UnitOfWork<T> unitOfWork;

    private boolean isTransactionOpen;

    public Repository(Connection conn, Mapper<T> mapper, UnitOfWork<T> unitOfWork) {
        this.conn = conn;
        this.mapper = mapper;
        this.unitOfWork = unitOfWork;
    }

    public Optional<T> findById(Long id) {
        return mapper.findById(id);
    }

    public Optional<T> findByString(String s) {
        return mapper.findByString(s);
    }

    public void beginTransaction() {
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        isTransactionOpen = true;
    }

    public Optional<T> create(T entity) {
        if (isTransactionOpen) {
            unitOfWork.registerNew(entity);
            return Optional.empty();
        } else {
            Optional<T> optionalT = mapper.insert(entity);
            clearIdentityMap();
            return optionalT;
        }
    }

    public void update(T entity) {
        if (isTransactionOpen) {
            unitOfWork.registerUpdate(entity);
        } else {
            mapper.update(entity);
            clearIdentityMap();
        }
    }

    public void delete(T entity) {
        if (isTransactionOpen) {
            unitOfWork.registerDelete(entity);
        } else {
            mapper.delete(entity);
            clearIdentityMap();
        }
    }

    private void clearIdentityMap() {
        mapper.getIdentityMap().clear();
    }

    public void commitTransaction() {
        if (!isTransactionOpen) return;
        unitOfWork.commit();
        try {
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearIdentityMap();
        isTransactionOpen = false;
    }

    public void rollbackTransaction() {
        unitOfWork.clearUnitOfWork();
        try {
            conn.setAutoCommit(true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        isTransactionOpen = false;
    }
}
