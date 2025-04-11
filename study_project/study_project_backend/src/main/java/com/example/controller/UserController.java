package com.example.controller;

import com.example.entity.AccountUser;
import com.example.entity.KeyPair;
import com.example.entity.RestBean;
import com.example.test.test;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    test test;
    @Autowired
    private KeyPair keypair; // 注入 KeyPair 对象
    @GetMapping("/me")
    public RestBean<Map<String, Object>> me(@SessionAttribute("account") AccountUser user){
        Map<String, String> keyPair = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            keyPair = test.generateKeyPair();
            String privateKey = keyPair.get("privateKey");
            String publicKey = keyPair.get("publicKey");
            System.out.println("私钥：" + privateKey);
            System.out.println("公钥：" + publicKey);
            keypair.setKeyPair(privateKey,publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //公钥和正常消息一起返回给客户端
        map.put("publicKeyHex", keyPair.get("publicKey").toString());
        map.put("user", user);
        return RestBean.success(map);
    }
}
