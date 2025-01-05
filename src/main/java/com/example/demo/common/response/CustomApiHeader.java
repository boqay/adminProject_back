package com.example.demo.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomApiHeader {
    private int code;
    private String message;
}
