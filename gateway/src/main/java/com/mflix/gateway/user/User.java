package com.mflix.gateway.user;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Collection;
import java.util.Date;

@Document("users")
public class User {

    @MongoId(FieldType.OBJECT_ID)
    public String id;

    public String name;

    public String email;

    public String password;

    public Collection<String> authorities;

    public Date creationDate;
}
