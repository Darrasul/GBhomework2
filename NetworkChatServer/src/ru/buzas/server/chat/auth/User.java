package ru.buzas.server.chat.auth;

import java.util.Objects;

public class User {

    private final String login;
    private final String password;
    private final String userName;
    public boolean onlineStatus;

    public User(String login, String password, String userName) {
        this.login = login;
        this.password = password;
        this.userName = userName;
        onlineStatus = false;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.userName = null;
        onlineStatus = false;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
