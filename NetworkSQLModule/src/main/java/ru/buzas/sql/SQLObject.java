package ru.buzas.sql;

public class SQLObject {

    private final String login;
    private final String password;
    private final String userName;

    public SQLObject(String login, String password, String userName) {
        this.login = login;
        this.password = password;
        this.userName = userName;
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
}
