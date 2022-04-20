package com.westernacher.test.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
}
