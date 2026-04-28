package com.megacoffee.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.megacoffee.modules.user.UserVO;
import com.megacoffee.modules.user.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("[LOGIN] Attempting to load user: " + username);
        UserVO user = userRepo.itemByUserID(username);
        if (user == null) {
            System.out.println("[LOGIN] User not found: " + username);
            throw new UsernameNotFoundException("User not found: " + username);
        }

        System.out.println("[LOGIN] SEQ : " + user.getSeq() + " , User found - ID: " + user.getUserId() + ", Password hash: " + (user.getPassword() != null ? user.getPassword().substring(0, Math.min(20, user.getPassword().length())) + "..." : "null"));
        
        return new CustomUserDetails(user.getUserId(), user.getPassword(),
            Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")),
            user.getSeq());
    }
}
