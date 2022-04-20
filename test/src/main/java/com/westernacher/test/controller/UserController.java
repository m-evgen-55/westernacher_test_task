package com.westernacher.test.controller;

import com.westernacher.test.controller.request.SortUserDetailsRequest;
import com.westernacher.test.controller.request.UserRequest;
import com.westernacher.test.controller.response.Response;
import com.westernacher.test.controller.response.ResponseWithBody;
import com.westernacher.test.exception.UserNotFoundException;
import com.westernacher.test.model.User;
import com.westernacher.test.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Api(value = "User")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    // TODO убрать полдьзователя и пароль от бд

    private final UserService userService;

    @ApiOperation(value = "Create new user", response = User.class)
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseWithBody<User> createUser(
            @ApiParam(name = "userRequest", required = true, value = "User request")
            @RequestBody final UserRequest userRequest
    ) {
        final ResponseWithBody<User> userResponseWithBody = new ResponseWithBody<>();
        try {
            userResponseWithBody
                    .setSuccess(true)
                    .setResponseBody(userService.createUser(userRequest));
        } catch (Exception e) {
            userResponseWithBody.setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return userResponseWithBody;
    }

    @ApiOperation(value = "Update user", response = User.class)
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseWithBody<User> updateUser(
            @ApiParam(name = "userId", required = true, value = "Id of user")
            @RequestParam("userId") final long userId,
            @ApiParam(name = "userRequest", required = true, value = "User request")
            @RequestBody final UserRequest userRequest
    ) {
        final ResponseWithBody<User> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(userService.updateUser(userId, userRequest));
        } catch (UserNotFoundException e) {
            response.setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Find user by id", response = User.class)
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public ResponseWithBody<User> findUserById(
            @ApiParam(name = "userId", required = true, value = "Id of user")
            @RequestParam("userId") final long userId
    ) {
        final ResponseWithBody<User> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(userService.findUserById(userId));
        } catch (UserNotFoundException e) {
            response.setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Find all users", response = User.class)
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ResponseWithBody<List<User>> findAllUsers(
            @ApiParam(name = "sortUserDetails", required = true, value = "Sort users list")
            @RequestBody final SortUserDetailsRequest sortUserDetails
    ) {
        final ResponseWithBody<List<User>> response = new ResponseWithBody<>();
        try {
            response
                    .setSuccess(true)
                    .setResponseBody(userService.findAllUsers(sortUserDetails));
        } catch (Exception e) {
            response.setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }

    @ApiOperation(value = "Delete user", response = Response.class)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Response deleteUser(
            @ApiParam(name = "userId", required = true, value = "Id of user")
            @RequestParam("userId") final long userId
    ) {
        final Response response = new Response();
        try {
            userService.deleteUser(userId);
            response
                    .setSuccess(true);
        } catch (UserNotFoundException e) {
            response.setSuccess(false)
                    .setErrors(Collections.singletonList(e.getMessage()));
        }
        return response;
    }
}
