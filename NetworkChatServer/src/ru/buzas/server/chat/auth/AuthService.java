package ru.buzas.server.chat.auth;

import ru.buzas.sql.SQLHandler;
import ru.buzas.sql.SQLObject;

import java.util.HashSet;
import java.util.Set;

public class AuthService {

    private Set<User> USERS_USER = new HashSet<>();
    private Set<SQLObject> USERS_SQL_OBJ = new HashSet<>();
    private SQLHandler sqlHandler = new SQLHandler();

    public String getUserNameByLoginAndPassword(String login, String password){
        User requiredUser = new User(login, password);
        for (User user : USERS_USER){
            if(requiredUser.equals(user)){
                return user.getUserName();
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

    public void renameUser(String oldUsername, String newUsername){
        for (User user : USERS_USER) {
            if (user.getUserName() == oldUsername){
                sqlHandler.renameUser(oldUsername, newUsername);
            }
        }
    }

    public void initAuthService(){
        transformIntoUser();
    }

    private void transformIntoUser() {
        sqlHandler.getUsersData(USERS_SQL_OBJ);
        for (SQLObject sqlObject : USERS_SQL_OBJ) {
            USERS_USER.add(new User(sqlObject.getLogin(), sqlObject.getPassword(), sqlObject.getUserName()));
        }
    }
}
