package com.demo.client.controller;

import com.demo.client.model.UserVo;
import com.demo.service.utils.JsonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "save user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserVo.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @PostMapping("/user")
    public UserVo saveUser(UserVo userVo) {
        log.info("user: {}", JsonUtil.objectToJson(userVo));
        return userVo;
    }
}
