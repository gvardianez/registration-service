package ru.alov.registration.orm;

import org.springframework.stereotype.Component;
import ru.alov.registration.entities.Entity;

import java.util.ArrayList;
import java.util.List;

@Component
public class UnitOfWork <T extends Entity> {

    private final Mapper<T> mapper;

    private final List<T> newUsers = new ArrayList<>();
    private final List<T> updateUsers = new ArrayList<>();
    private final List<T> deleteUsers = new ArrayList<>();

    public UnitOfWork(Mapper<T> mapper) {
        this.mapper = mapper;
    }

    public void registerNew(T entity) {
        entity.markNew();
        this.newUsers.add(entity);
    }

    public void registerUpdate(T entity) {
        entity.markUpdate();
        this.updateUsers.add(entity);
    }

    public void registerDelete(T entity) {
        entity.markRemoved();
        this.newUsers.remove(entity);
        this.updateUsers.remove(entity);
        this.deleteUsers.add(entity);
    }

    public void commit() {
        insertNew();
        update();
        delete();
        clearUnitOfWork();
    }

    public void clearUnitOfWork() {
        newUsers.clear();
        updateUsers.clear();
        deleteUsers.clear();
    }

    private void insertNew() {
       mapper.insert(newUsers);
    }

    private void update() {
        mapper.update(updateUsers);
    }

    private void delete() {
        mapper.delete(deleteUsers);
    }
}
