package com.jukusoft.mmo.core.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount)) return false;
        if (!super.equals(o)) return false;
        UserAccount that = (UserAccount) o;
        return userID == that.userID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userID);
    }

}
