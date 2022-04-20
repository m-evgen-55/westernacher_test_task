package com.westernacher.test.controller.request;

import com.westernacher.test.model.SortUserDetails;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SortUserDetailsRequest {

    private SortUserDetails sortUserDetails;
}
