package com.mflix.user;

import com.mflix.annotation.ResponseConverter;
import com.mflix.core.common.Converter;

@ResponseConverter
public class UserConverter implements Converter {

    @Override
    public Object convert(Object obj) {
        User user = (User) obj;
        return new UserResponse(user.id, user.name, user.email);
    }

    @Override
    public boolean supports(Object obj) {
        return obj instanceof User;
    }
}
