package ru.alov.registration.dto;

public class UserProfileDto {

    private Long id;

    private String login;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserProfileDto() {
    }

    public UserProfileDto(Long id, String login) {
        this.id = id;
        this.login = login;
    }
}
