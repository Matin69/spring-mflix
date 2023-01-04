package com.mflix.app.security;

import com.mflix.app.user.User;
import com.mflix.app.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class SecurityUserLoader implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityUserLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User loadedUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not found a user with given name"));
        return new org.springframework.security.core.userdetails.User(
                loadedUser.email,
                loadedUser.password,
                true,
                true,
                true,
                true,
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
