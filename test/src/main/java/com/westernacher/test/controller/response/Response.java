package com.westernacher.test.controller.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class Response {

    private boolean success;
    private List<String> errors;
}
