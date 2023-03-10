package com.mflix.user;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Document("users")
public class User {

    @MongoId(FieldType.OBJECT_ID)
    public String id;

    public String name;

    public String email;

    public String password;

    public Date creationDate;

    public List<Role> roles;
}
