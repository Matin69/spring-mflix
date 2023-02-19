package com.mflix.gateway.security;

import com.mflix.gateway.user.User;
import com.mflix.gateway.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class SecurityUserLoader implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityUserLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User loadedUser = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not found a user with given name"));
        return new org.springframework.security.core.userdetails.User(
                loadedUser.name,
                loadedUser.password,
                true,
                true,
                true,
                true,
                getAuthorities(loadedUser)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User loadedUser) {
        return loadedUser.authorities
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
