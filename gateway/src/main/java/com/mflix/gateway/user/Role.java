package com.mflix.gateway.user;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("roles")
public class Role {

    public String name;

    public List<String> permissions;

    public Role(List<String> permissions) {
        this.permissions = permissions;
    }
}
