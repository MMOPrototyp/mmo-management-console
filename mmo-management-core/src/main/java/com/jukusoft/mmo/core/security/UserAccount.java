package com.jukusoft.mmo.core.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAccount extends UsernamePasswordAuthenticationToken {

    private final long userID;

    public UserAccount(long userID, Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(principal, null, authorities);
        this.userID = userID;
    }

    public long getUserID() {
        return this.userID;
    }

    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

}
