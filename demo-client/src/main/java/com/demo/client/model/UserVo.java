package com.demo.client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserVo {
    @NotBlank
    @Size(min = 0, max = 20)
    private String Name;
    @NotBlank
    private Integer age;
}
