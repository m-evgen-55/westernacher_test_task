package com.westernacher.test.utils;

import com.westernacher.test.model.User;
import com.westernacher.test.model.entity.UserEntity;

public class UserUtils {

    public static User mapUserEntityToUser(final UserEntity userEntity) {
        return new User()
                .setId(userEntity.getId())
                .setFirstName(userEntity.getFirstName())
                .setLastName(userEntity.getLastName())
                .setEmail(userEntity.getEmail())
                .setBirthDate(userEntity.getBirthDate());
    }
}
