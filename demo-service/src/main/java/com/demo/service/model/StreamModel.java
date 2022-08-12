package com.demo.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StreamModel {
    private String name;
    private int age;
    private SingleModel singleModel;
}
