package ru.buzas.server.chat.auth;

import java.sql.*;

public class DataBaseAuthService implements AuthInterface {

    private static final String DataBaseURL = "jdbc:sqlite:onlineChatBase";
    private Connection connection;
    private PreparedStatement getUsernameStatement;
    private PreparedStatement updateUsernameStatement;

    @Override
    public void start() {
        try {
            System.out.println("DB connection creation");
            connection = DriverManager.getConnection(DataBaseURL);
            System.out.println("DB connection is create");
            getUsernameStatement = setGetUsernameStatement();
            updateUsernameStatement = setUpdateUsernameStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.err.println("Failed to connect to DB by URL: " + DataBaseURL);
            throw new RuntimeException("Failed to start auth service");
        }

    }

    @Override
    public void stop() {
        if (connection != null){
            try {
                System.out.println("DB connection close");
                connection.close();
                System.out.println("DB connection closed");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.err.println("Failed to close connection to DB by URL: " + DataBaseURL);
                throw new RuntimeException("Failed to stop auth service");
            }
        }
    }

    @Override
    public String getUserNameByLoginAndPassword(String login, String password) {
        String username = null;
        try {
            getUsernameStatement.setString(1, login);
            getUsernameStatement.setString(2, password);
            ResultSet resultSet = getUsernameStatement.executeQuery();
            while (resultSet.next()){
                username = resultSet.getString("username");
                break;
            }
            resultSet.close();
        } catch (SQLException throwables) {
            System.err.println("Failed to get username from DB by login: " + login + " and password: " + password);
            throwables.printStackTrace();
        }
        return username;
    }

    @Override
    public void updateUsername(String oldUsername, String newUsername) {
        if (!oldUsername.equals(newUsername)){
            try {
                updateUsernameStatement.setString(1, newUsername);
                updateUsernameStatement.setString(2, oldUsername);
                int result = updateUsernameStatement.executeUpdate();
                System.out.println("Username updated. Updated rows: " + result);
            } catch (SQLException throwables) {
                System.err.println("Failed to update username. New name: " + newUsername + " and old one: " + oldUsername);
                throwables.printStackTrace();
            }
        }
    }

    private PreparedStatement setGetUsernameStatement() throws SQLException {
        return connection.prepareStatement("SELECT username FROM users WHERE login = ? AND password = ?");
    }

    private PreparedStatement setUpdateUsernameStatement() throws SQLException {
        return connection.prepareStatement("UPDATE users SET username = ? WHERE username = ?");
    }
}
