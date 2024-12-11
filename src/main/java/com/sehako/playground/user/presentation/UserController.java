package com.sehako.playground.user.presentation;


import com.sehako.playground.common.code.ErrorCode;
import com.sehako.playground.common.exception.CommonException;
import com.sehako.playground.user.presentation.request.UserRegisterRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/test/{param}")
    public void paramTypeMisMatch(@PathVariable int param) {
        throw new CommonException(ErrorCode.SERVER_ERROR);
    }

    @PostMapping("/test")
    public void validFailure(@RequestBody @Valid UserRegisterRequest request) {
    }
}
