package com.demo.client.exception;

import lombok.Getter;

@Getter
public enum DemoCode {
    SUCCESS(0, "success"),
    INTEGRATION_ERROR(800, "integration error"),
    SYSTEM_ERROR(999, "system_error");

    private int code;
    private String message;

    DemoCode(){
        //do nothing
    }

    private DemoCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
