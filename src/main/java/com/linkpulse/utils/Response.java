package com.linkpulse.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response<T extends BaseResponse> {
    private Boolean success;
    private String message;
    private T data;

}
