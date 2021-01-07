package com.demo.service.model;

import lombok.Data;

@Data
public class ProduceRes<T> {

    public ProduceRes(){
        //do nothing
    }

    public ProduceRes(T data){
        this.data = data;
    }

    private int code;
    private String message;
    private T data;
}
