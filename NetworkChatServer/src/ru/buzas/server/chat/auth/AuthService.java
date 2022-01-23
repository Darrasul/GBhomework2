package ru.buzas.server.chat.auth;

import java.util.Set;

public class AuthService {

    private static Set<User> USERS = Set.of(
            new User("login1", "pass1", "username1"),
            new User("login2", "pass2", "username2"),
            new User("login3", "pass3", "username3")
    );

    public String getUserNameByLoginAndPassword(String login, String password){
        User requiredUser = new User(login, password);
        for (User user : USERS){
            if(requiredUser.equals(user)){
                return user.getUserName();
            }
        }
        return null;
    }

    public boolean onlineAccess(String name){
        for (User user : USERS){
            if (name.equals(user.getUserName())){
                return user.isOnlineStatus();
            }
        }
        return false;
    }

    public void setOnlineAccess(String name, boolean isItOnline){
        for (User user : USERS){
            if (name.equals(user.getUserName())){
                user.setOnlineStatus(isItOnline);
            }
        }
    }
}
