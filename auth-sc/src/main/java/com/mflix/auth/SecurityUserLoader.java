package com.mflix.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityUserLoader implements UserDetailsService {

    private final UserApi userApi;

    public SecurityUserLoader(UserApi userApi) {
        this.userApi = userApi;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User loadedUser = userApi.search(new UserSearchParams(username));
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
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : loadedUser.roles) {
            authorities.addAll(
                    role.permissions
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );
        }
        return authorities;
    }
}
