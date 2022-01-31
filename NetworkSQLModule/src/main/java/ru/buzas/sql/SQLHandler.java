package ru.buzas.sql;

import java.sql.*;
import java.util.Set;

public class SQLHandler {

    public static final String ONLINE_CHAT_BASE_URL = "jdbc:sqlite:D:/Work/Learning/Уроки 2/GBhomework2/onlineChatBase";
    private static Connection connection;
    private Set<String> loginSet;
    private Set<String> passwordSet;
    private Set<String> usernameSet;

    public void addUser (String login, String password, String username){
        try {
            connectToSQL();
            PreparedStatement addStatement =
                    connection.prepareStatement("insert into users (login, password, username) VALUES (?, ?, ?)");
            addUserFinal(login, password, username, addStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnectFromSQL();
        }
    }

    public void renameUser(String oldUsername, String newUsername){
        try {
            PreparedStatement renameStatement =
                    connection.prepareStatement("update users set username = ? where username = ?");
            renameUserFinal(oldUsername, newUsername, renameStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(String username){
        try {
            PreparedStatement deleteStatement =
                    connection.prepareStatement("delete from users where username = ?");
            deleteUserFinal(username, deleteStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getUsersData(Set<SQLObject> USERS){
        try {
            connectToSQL();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()){
                String login = resultSet.getString(2);
                String password = resultSet.getString(3);
                String username = resultSet.getString(4);
                USERS.add(new SQLObject(login, password, username));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnectFromSQL();
        }
    }

    private void addUserFinal(String login, String password, String username, PreparedStatement statement){
        try {
            connectToSQL();
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, username);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnectFromSQL();
        }
    }

    private void renameUserFinal(String oldUsername, String newUsername, PreparedStatement statement){
        try {
            connectToSQL();
            statement.setString(1, oldUsername);
            statement.setString(2, newUsername);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnectFromSQL();
        }
    }

    private void deleteUserFinal(String username, PreparedStatement statement){
        try {
            connectToSQL();
            statement.setString(1, username);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnectFromSQL();
        }
    }

    private static void connectToSQL() throws SQLException {
        connection = DriverManager.getConnection(ONLINE_CHAT_BASE_URL);
    }


    private static void disconnectFromSQL() {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
