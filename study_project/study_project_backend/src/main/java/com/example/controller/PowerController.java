package com.example.controller;

import com.example.entity.*;
import com.example.mapper.PowerMapper;
import com.example.mapper.UserMapper;
import com.example.test.SM2Util;
import com.example.service.PowerService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/api/power")
public class PowerController {
    @Resource
    PowerService service;
    @Resource
    UserMapper mapper;
    @Resource
    PowerMapper powermapper;
    @Autowired
    private KeyPair keypair; // 注入 KeyPair 对象

    @PostMapping("/trade")
    public RestBean<String> Agree(@RequestParam("pid") String pid,
                                  @RequestParam("decryptData") String decryptData,
                                  @RequestParam("signatureData") String signatureData,
                                  @RequestParam("username") String username) throws Exception {
        Account account = mapper.findAccountByNameOrEmail(username);
        String publicKey = account.getPublicKey();
        System.out.println("sa"+decryptData);
        System.out.println("sa"+signatureData);
        System.out.println("sa"+publicKey);

        //验签
        if (SM2Util.verify(decryptData, publicKey, signatureData)) {
            try {
                if (service.Trade(pid, username)) {
                    //获取阿里云OSS图片链接
                    //1.由pid解码得到图片链接
                    //String url = AESUtil.decrypt(pid);
                    //2.图片链接加密
                    //String deData = SM2Util.encrypt(url, publicKey);

                    return RestBean.success(pid);
                } else
                    return RestBean.failure(400, "trade");

            } catch (Exception e) {
                e.printStackTrace();
                return RestBean.failure(400, "trade");
            }
        } else {
            System.out.println("错误：" + SM2Util.verify(decryptData, publicKey, signatureData));
            return RestBean.failure(400, "非法用户");
        }

    }

    @PostMapping("/addorder")
    public RestBean<String> Addorder(@RequestParam("from") String from,
                                     @RequestParam("to") String to,
                                     @RequestParam("work_name") String work_name,
                                     @RequestParam("receiptID") String receiptID,
                                     @RequestParam("recordId") String recordId,
                                     @RequestParam("time") String time,
                                     @RequestParam("status") String status) throws Exception {
        try {
            if (service.addOrder(from, to, work_name, receiptID, recordId, time, status)) {
                return RestBean.success(receiptID);
            } else
                return RestBean.failure(400, "Addorder");

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(400, "Addorder");
        }
    }
    @PostMapping("/agreeorder")
    public RestBean<String> Agreeorder(@RequestParam("recordId") String recordId,
                                     @RequestParam("status") String status) throws Exception {
        System.out.println(recordId);
        System.out.println(status);
        try {
            if (service.changeOrderstatus(recordId, status)) {
                return RestBean.success(recordId);
            } else
                return RestBean.failure(400, "agreeorder");

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(400, "agreeorder");
        }
    }
    @PostMapping("/refuseorder")
    public RestBean<String> refuseorder(@RequestParam("recordId") String recordId,
                                     @RequestParam("status") String status) throws Exception {
        try {
            if (service.changeOrderstatus(recordId,status)) {
                return RestBean.success(recordId);
            } else
                return RestBean.failure(400, "refuseorder");

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(400, "refuseorder");
        }
    }

    @PostMapping("/look1")
    public RestBean<Map<Object, Object>> Look1(@RequestParam("from") String from,
                                               @RequestParam("to") String to) {
        try {
            System.out.println(from);
            System.out.println(to);
            Map<Object, Object> map = new HashMap<>();
            String choice = "1";
            // 调用 service 方法获取图片列表
            List<Picture> pictureList = service.getPictureList(from, to, choice);
            List<String> url = service.getUrlList();
            map.put("sa", pictureList);
            map.put("sb", url);
            System.out.println(pictureList);
            // 如果成功获取到图片列表，则返回成功的响应
            return RestBean.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果发生异常，返回失败的响应
            return RestBean.failure(400);
        }
    }

    @PostMapping("/look2")
    public RestBean<List<Picture>> Look2(@RequestParam("from") String from,
                                         @RequestParam("to") String to) {
        try {
            System.out.println(from);
            System.out.println(to);

            String choice = "2";
            // 调用 service 方法获取图片列表
            List<Picture> pictureList = service.getPictureList(from, to, choice);
            System.out.println(pictureList);
            // 如果成功获取到图片列表，则返回成功的响应
            return RestBean.success(pictureList);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果发生异常，返回失败的响应
            return RestBean.failure(400);
        }
    }

    @PostMapping("/look3")
    public RestBean<Map<Object, Object>> Look3(@RequestParam("from") String from,
                                               @RequestParam("to") String to,
                                               @RequestParam("username") String username) {
        try {
            System.out.println(from);
            System.out.println(to);
            System.out.println(username);
            Map<Object, Object> map = new HashMap<>();

            // 调用 service 方法获取图片列表
            List<Picture> pictureList = service.getPictureListByUsername(from, to, username);
            List<String> url = service.getUrlListByUsername(from, to, username);
            System.out.println(pictureList);
            map.put("sa", pictureList);
            map.put("sb", url);
            // 如果成功获取到图片列表，则返回成功的响应
            return RestBean.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果发生异常，返回失败的响应
            return RestBean.failure(400);
        }

    }

    @PostMapping("/look4")
    public RestBean<Map<Object, Object>> Look4(@RequestParam("from") String from,
                                               @RequestParam("to") String to,
                                               @RequestParam("username") String username) {
        try {
            System.out.println(from);
            System.out.println(to);
            System.out.println(username);
            Map<Object, Object> map = new HashMap<>();

            // 调用 service 方法获取图片列表
            List<Order> orderList = service.getOrderListByUsername(from, to, username);
            List<String> url = service.getUrlListByUsername(from, to, username);
            System.out.println(orderList);
            System.out.println(url);
            map.put("sa", orderList);
            map.put("sb", url);
            // 如果成功获取到图片列表，则返回成功的响应
            return RestBean.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果发生异常，返回失败的响应
            return RestBean.failure(400);
        }

    }
    @PostMapping("/LookMessage")
    public RestBean<Map<Object, Object>> LookMessage(@RequestParam("user") String user) {
        try {
            System.out.println("user:" + user);
            Map<Object, Object> map = new HashMap<>();
            // 调用 service 方法获取图片列表
            List<Picture> pictureList = service.getPictureList1(user);
            List<String> url = service.getUrlList1(user);
            map.put("sa", pictureList);
            map.put("sb", url);
            System.out.println(pictureList);
            // 如果成功获取到图片列表，则返回成功的响应
            return RestBean.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果发生异常，返回失败的响应
            return RestBean.failure(400);
        }
    }
    @PostMapping("/MessageReset")
    public RestBean<String> MessageReset(@RequestParam("pid") String pid,@RequestParam("choice") String choice) {
        try {
            if(choice.equals("1")){
                mapper.Reset1(pid);
            }else {
                mapper.Reset2(pid);
            }
            return RestBean.success("if_agree重置为" + choice);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果发生异常，返回失败的响应
            return RestBean.failure(400);
        }
    }
    @GetMapping("/search")
    public RestBean<List<Picture>> search(@RequestParam(required = false ,value = "searchKeyword") String keyWord) {
        try {
            System.out.println(keyWord);
            List<Picture> pictures = powermapper.findByTypeOne();
            //pictures.removeIf(u-> !u.getName().contains(keyWord)&&!u.getUsername().contains(keyWord));
            // 如果关键词不为空，则进行过滤
            if (keyWord != null && !keyWord.trim().isEmpty()) {
                pictures.removeIf(u -> !u.getWork_name().contains(keyWord) && !u.getTo().contains(keyWord));
            }
            System.out.println(pictures);
            return RestBean.success(pictures);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果发生异常，返回失败的响应
            return RestBean.failure(400);
        }
    }
}
