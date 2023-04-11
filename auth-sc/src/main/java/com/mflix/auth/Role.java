package com.mflix.auth;

import java.util.List;

public class Role {

    public String name;

    public List<String> permissions;

    public Role(List<String> permissions) {
        this.permissions = permissions;
    }
}
