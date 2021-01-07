package com.demo.service.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestModel {
    @ApiModelProperty("名稱")
    private String name;
}
