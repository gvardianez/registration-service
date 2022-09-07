package ru.alov.registration.repositories;

import org.springframework.stereotype.Component;
import ru.alov.registration.entities.User;
import ru.alov.registration.orm.Mapper;
import ru.alov.registration.orm.UnitOfWork;
import ru.alov.registration.orm.UserMapper;

import java.sql.Connection;
import java.util.Optional;

@Component
public class UserRepository extends Repository<User> {

    private final UserMapper userMapper;

    public UserRepository(Connection conn, Mapper<User> mapper, UnitOfWork<User> unitOfWork, UserMapper userMapper) {
        super(conn, mapper, unitOfWork);
        this.userMapper = userMapper;
    }

    public Optional<User> findByLogin(String login) {
        return userMapper.findByString(login);
    }


}
