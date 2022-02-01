package ru.buzas.server.chat.auth;

public interface AuthInterface {
    default void start(){
//        Do nothing
    };
    default void stop(){
//        Do nothing
    };
    default void setOnlineAccess(String name, boolean isItOnline){
//        Do nothing
    }
    String getUserNameByLoginAndPassword(String login, String password);
    void updateUsername(String oldUsername, String newUsername);

}
