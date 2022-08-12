package com.demo.client.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVo {
    @NotBlank
    @Size(min = 0, max = 20)
    private String name;
    @NotBlank
    private Integer age;
}
