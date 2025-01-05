package com.example.demo.common.exception;

import com.example.demo.common.response.ResponseCode;

public class MemberException extends BaseException {
    public MemberException(ResponseCode responseCode) {
        super(responseCode);
    }
}
