package ru.alov.registration.orm;

import org.springframework.stereotype.Component;
import ru.alov.registration.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserMapper implements Mapper<User> {

    private final PreparedStatement selectUserById;

    private final PreparedStatement selectUserByLogin;

    private final PreparedStatement insertUser;

    private final PreparedStatement updateUser;

    private final PreparedStatement deleteUser;

    private final Map<Long, User> identityMap;

    public UserMapper(Connection conn) {
        this.identityMap = new HashMap<>();
        try {
            this.selectUserById = conn.prepareStatement("select id, username, password from users where id = ?");
            this.selectUserByLogin = conn.prepareStatement("select id, username, password from users where username = ?");
            this.insertUser = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?);");
            this.updateUser = conn.prepareStatement("UPDATE users SET username = ?, password = ? WHERE id = ?");
            this.deleteUser = conn.prepareStatement("DELETE FROM users WHERE id = ?");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Map<Long, User> getIdentityMap() {
        return identityMap;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = identityMap.get(id);
        if (user != null) {
            return Optional.of(user);
        }
        try {
            selectUserById.setLong(1, id);
            ResultSet rs = selectUserById.executeQuery();
            if (rs.next()) {
                user = new User(rs.getLong(1), rs.getString(2), rs.getString(3));
                identityMap.put(id, user);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.empty();
    }

    public Optional<User> findByString(String login) {
        for (Map.Entry<Long, User> entry : identityMap.entrySet()) {
            if (entry.getValue().getLogin().equals(login)) return Optional.of(entry.getValue());
        }
        try {
            selectUserByLogin.setString(1, login);
            ResultSet rs = selectUserByLogin.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getLong(1), rs.getString(2), rs.getString(3));
                identityMap.put(user.getId(), user);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return Optional.empty();
    }

    @Override
    public void update(User user) {
        if (user.getId() == null) throw new IllegalStateException();
        try {
            updateUser.setString(1, user.getLogin());
            updateUser.setString(2, user.getPassword());
            updateUser.setLong(3, user.getId());
            System.out.println(user.getPassword());
            updateUser.executeUpdate();
            identityMap.put(user.getId(), user);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void update(List<User> users) {
        try {
            for (User user : users) {
                updateUser.setString(1, user.getLogin());
                updateUser.setString(2, user.getPassword());
                updateUser.setLong(3, user.getId());
                updateUser.addBatch();
                identityMap.put(user.getId(), user);
            }
            updateUser.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> insert(User user) {
        if (user.getId() != null) throw new IllegalStateException();
        if (checkUserLogin(user).isPresent()) throw new IllegalStateException();
        try {
            insertUser.setString(1, user.getLogin());
            insertUser.setString(2, user.getPassword());
            insertUser.executeUpdate();
            return findByString(user.getLogin());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void insert(List<User> users) {
        try {
            for (User user : users) {
                if (checkUserLogin(user).isPresent()) throw new IllegalStateException();
                insertUser.setString(1, user.getLogin());
                insertUser.setString(2, user.getPassword());
                insertUser.addBatch();
            }
            insertUser.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private Optional<User> checkUserLogin(User user) {
        for (Map.Entry<Long, User> entry : identityMap.entrySet()) {
            if (entry.getValue().getLogin().equals(user.getLogin())) return Optional.of(entry.getValue());
        }
        return Optional.empty();
    }

    @Override
    public void delete(User user) {
        try {
            identityMap.remove(user.getId());
            deleteUser.setLong(1, user.getId());
            deleteUser.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(List<User> users) {
        try {
            for (User user : users) {
                identityMap.remove(user.getId());
                deleteUser.setLong(1, user.getId());
                deleteUser.addBatch();
            }
            deleteUser.executeBatch();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
