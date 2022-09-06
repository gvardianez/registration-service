package ru.alov.registration.entities;

import lombok.Data;

import java.util.Objects;

@Data
public class User implements Entity {

    private Long id;

    private String login;

    private String password;

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && login.equals(user.login) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }

    @Override
    public void markNew() {
        if (this.getId() != null || this.getLogin() == null || this.getPassword() == null)
            throw new IllegalStateException();
    }

    @Override
    public void markUpdate() {
        if (this.getId() == null) throw new IllegalStateException();
    }

    @Override
    public void markRemoved() {
        if (this.getId() == null) throw new IllegalStateException();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
