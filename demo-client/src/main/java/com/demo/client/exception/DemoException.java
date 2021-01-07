package com.demo.client.exception;

import lombok.Getter;

@Getter
public class DemoException extends RuntimeException {
    public DemoException(){

    }

    public DemoException(String msg){
        super(msg);
    }

    private DemoCode demoCode;

    private String message;

    private Exception exception;

    public DemoException(DemoCode demoCode){
        super(demoCode.toString());
        this.demoCode = demoCode;
    }
    public DemoException(DemoCode demoCode, String message){
        super(demoCode.toString());
        this.demoCode = demoCode;
        this.message = message;
    }

    public DemoException(DemoCode demoCode, Exception exception) {
        super(demoCode.toString());
        this.demoCode = demoCode;
        this.exception = exception;
    }
}
