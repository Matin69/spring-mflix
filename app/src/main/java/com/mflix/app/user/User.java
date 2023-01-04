package com.mflix.app.user;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("users")
public class User {

    @MongoId(FieldType.OBJECT_ID)
    public String id;

    public String name;

    public String email;

    public String password;
}
