package com.westernacher.test.service;

import com.westernacher.test.controller.request.SortUserDetailsRequest;
import com.westernacher.test.controller.request.UserRequest;
import com.westernacher.test.exception.UserNotFoundException;
import com.westernacher.test.model.User;

import java.util.List;

public interface UserService {

    User createUser(UserRequest userRequest);

    User updateUser(long userId, UserRequest userRequest) throws UserNotFoundException;

    User findUserById(long userId) throws UserNotFoundException;

    List<User> findAllUsers(SortUserDetailsRequest sortUserDetails);

    void deleteUser(long userId) throws UserNotFoundException;
}
