package com.megacoffee.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private Long seq;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long seq) {
        super(username, password, authorities);
        this.seq = seq;
    }

    public Long getSeq() {
        return seq;
    }
}
