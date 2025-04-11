package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.AuthorService;
import jakarta.annotation.Resource;
import javax.validation.constraints.Pattern;

import jakarta.servlet.http.HttpSession;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorController {
    @Resource
    AuthorService service;

    @PostMapping("/vaild-register-email")
    public RestBean<String> validateRegisterEmail(@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$") @RequestParam("email")String email,
                                          HttpSession session){
        if(service.sendVaildateEmail(email, session.getId(),false) == null)
            return RestBean.success("验证码发送成功，请注意查收");
        else
            return RestBean.failure(400,service.sendVaildateEmail(email, session.getId(),false));
    }
    @PostMapping("/vaild-reset-email")
    public RestBean<String> validateResetEmail(@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$") @RequestParam("email")String email,
                                                  HttpSession session){
        if(service.sendVaildateEmail(email, session.getId(),true) == null)
            return RestBean.success("验证码发送成功，请注意查收");
        else
            return RestBean.failure(400,service.sendVaildateEmail(email, session.getId(),true));
    }

    @PostMapping("/register")
    public RestBean<String> registerUser(@RequestParam("username")String username,
                                         @RequestParam("password")String password,
                                         @RequestParam("email")String email,
                                         @RequestParam("code")String code,
                                         @RequestParam("publicKey")String publicKey,
                                         HttpSession session){
        if(service.vaildateAndRegister(username, password, email, code, publicKey,session.getId())==null)
            return RestBean.success("注册成功");
        else
            return RestBean.failure(400,service.vaildateAndRegister(username, password, email, code, publicKey, session.getId()));
    }
    @PostMapping("/start-reset")
    public RestBean<String> startReset(@RequestParam("email")String email,
                                         @RequestParam("code")String code,
                                         HttpSession session){
        if(service.vaildateOnly(email, code, session.getId())==null) {
            session.setAttribute("reset-password", email);
            return RestBean.success("验证通过");
        }
        else
            return RestBean.failure(400,service.vaildateOnly(email, code, session.getId()));
    }
    @PostMapping("/do-reset")
    public RestBean<String> doReset(@RequestParam("password")String password,
                                       HttpSession session){
        String email = (String) session.getAttribute("reset-password");
        session.removeAttribute("reset-password");
        if(service.resetPassword(password, email)) {
            return RestBean.success("重置密码成功");
        }
        else
            return RestBean.failure(400,"重置密码失败，请联系管理员");
    }
    @PostMapping("/logout")
    public RestBean<String> logout(HttpSession session){
            return RestBean.success("成功退出");
    }
}
