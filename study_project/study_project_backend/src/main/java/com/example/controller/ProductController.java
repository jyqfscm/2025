package com.example.controller;

import com.example.entity.Order_me;
import com.example.entity.Product;
import com.example.entity.RestBean;
import com.example.service.AuthorService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/api/product")
public class ProductController {
    @Resource
    AuthorService service;
    static int ans;

    @PostMapping("/getIds")
    public ResponseEntity<Map<String, String>> getIds(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();

        try {
            String cname = request.get("cname");
            String sname = request.get("sname");

            String salerId = null;
            String buyerId = null;

            if (salerId == null || buyerId == null) {
                response.put("error", "An error occurred while processing the request.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            } else {
                response.put("saler_id", salerId);
                response.put("buyer_id", buyerId);
                return ResponseEntity.ok(response);
            }

        } catch (Exception e) {
            // Handle exceptions, log errors, etc.
            response.put("error", "An error occurred while processing the request.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/addbuycar")
    public RestBean<String> addbuycar(@RequestBody Product request) {

        try {
            String id = null;
            String name = null;
            String cname = null;
            String price = null;

            if (service.addBuyCar(id, name, cname, price) == null)
                return RestBean.success("添加购物车成功");
            else
                return RestBean.failure(400, "添加购物车失败，请联系管理员");

        } catch (Exception e) {

            return RestBean.failure(400, "添加购物车失败，请联系管理员");
        }
    }



}
