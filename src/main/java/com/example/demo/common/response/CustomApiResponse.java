package com.example.demo.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomApiResponse<T> {

    private CustomApiHeader header;
    private T data;
    private String msg;

    private static final int SUCCESS = 200;

    private CustomApiResponse(CustomApiHeader header, T data, String msg) {
        this.header = header;
        this.data = data;
        this.msg = msg;
    }

    public static <T> CustomApiResponse<T> success(T data, String message) {
        return new CustomApiResponse<T>(new CustomApiHeader(SUCCESS, "SUCCESS"), data, message);
    }

    public static <T> CustomApiResponse<T> fail(ResponseCode responseCode, T data) {
        return new CustomApiResponse<T>(new CustomApiHeader(responseCode.getHttpStatusCode(), responseCode.getMessage()), data, responseCode.getMessage());
    }
}
