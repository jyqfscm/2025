package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.FiscoService;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/api/fisco")
public class FiscoController {
    @Resource
    FiscoService service;
    @PostMapping("/one")
    public RestBean<String> one(@RequestParam("imagebase")String imagebase,
                                @RequestParam("username")String username,
                                @RequestParam("pid")String pid,
                                @RequestParam("time")String time,
                                @RequestParam("theme")String theme,
                                @RequestParam("pid_marked")String pid_marked,
                                @RequestParam("markcode")String markcode) {
        System.out.println("out:");
        System.out.println(imagebase);
        System.out.println(username);
        System.out.println(pid);
        System.out.println(time);
        System.out.println(theme);
        System.out.println(pid_marked);
        System.out.println(markcode);
        try {
            if (service.one(username, imagebase, pid ,time, theme, pid_marked, markcode))
                return RestBean.success("上传图片成功");
            else
                return RestBean.failure(400, "1上传图片失败，请联系管理员");
        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(400, "2上传图片失败，请联系管理员");
        }
    }

    @PostMapping("/two")
    public RestBean<String> two(@RequestParam("username")String username) {
        try {
            if (service.two(username) == null)
                return RestBean.failure(400, "查询失败，请联系管理员");
            else
                return RestBean.success(service.two(username));

        } catch (Exception e) {

            return RestBean.failure(400, "查询失败，请联系管理员");
        }
    }

    @PostMapping("/three")
    public RestBean<String> three(@RequestParam("from")String from,
                                  @RequestParam("to")String to,
                                  @RequestParam("imagebase")String imagebase,
                                  @RequestParam("receiptID")String receiptID,
                                  @RequestParam("recordId")String recordId) {

        try {
            System.out.println("receiptID: " + receiptID);
            System.out.println("from: " + from);
            System.out.println("to: " + to);
            System.out.println("itemName: " + imagebase);
            System.out.println("recordId: " + recordId);
            if (service.three(from, to, imagebase,receiptID, recordId) == null)
                return RestBean.success("添加订单成功");
            else
                return RestBean.failure(400, "添加订单失败，请联系管理员");

        } catch (Exception e) {

            return RestBean.failure(400, "添加订单失败，请联系管理员");
        }
    }












}
