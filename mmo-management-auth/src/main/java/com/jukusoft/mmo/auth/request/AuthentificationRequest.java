package com.jukusoft.mmo.auth.request;

//see also: https://gitlab.com/ertantoker/tutorials/spring-boot-security-jwt-example/-/blob/master/spring-boot-authentication-service/src/main/java/de/ertantoker/tutorial/request/AuthenticationRequest.java
public class AuthentificationRequest {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
