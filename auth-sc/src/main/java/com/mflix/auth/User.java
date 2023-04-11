package com.mflix.auth;

import java.util.Date;
import java.util.List;

public class User {

    public String id;

    public String name;

    public String email;

    public String password;

    public Date creationDate;

    public List<Role> roles;
}
