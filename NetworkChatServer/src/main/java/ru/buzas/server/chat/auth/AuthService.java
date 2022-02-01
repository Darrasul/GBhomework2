package ru.buzas.server.chat.auth;


import java.util.HashSet;
import java.util.Set;

public class AuthService implements AuthInterface {

    private Set<User> USERS_USER = new HashSet<>();

    public String getUserNameByLoginAndPassword(String login, String password){
        User requiredUser = new User(login, password);
        for (User user : USERS_USER){
            if(requiredUser.equals(user)){
                return user.getUserName();
            }
        }
        return null;
    }

    private User getUserByUsername(String username) {
        for (User user : USERS_USER) {
            if (user.getUserName().equals(username)){
                return user;
            }
        }
        return null;
    }

    public boolean onlineAccess(String name){
        for (User user : USERS_USER){
            if (name.equals(user.getUserName())){
                return user.isOnlineStatus();
            }
        }
        return false;
    }

    public void setOnlineAccess(String name, boolean isItOnline){
        for (User user : USERS_USER){
            if (name.equals(user.getUserName())){
                user.setOnlineStatus(isItOnline);
            }
        }
    }

    @Override
    public void updateUsername(String oldUsername, String newUsername) {
        User user = getUserByUsername(oldUsername);
        for (User oldUser : USERS_USER) {
            if (oldUser.getUserName().equals(newUsername)){
                System.err.println("Username already exist");
                return;
            }
        }
        if (user != null){
            user.setUserName(newUsername);
        }
    }
}
