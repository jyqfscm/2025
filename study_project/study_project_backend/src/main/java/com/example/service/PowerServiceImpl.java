package com.example.service;

import com.example.entity.Order;
import com.example.entity.Picture;
import com.example.mapper.PowerMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PowerServiceImpl  implements PowerService {
    @Resource
    PowerMapper mapper;

    @Override
    public boolean Trade(String pid,String username) {
        //获取整个图片的信息
        Picture picture = mapper.findByPid(pid);
//        mapper.updateOwner(pid,username);
//        String secret = picture.getPid();
        //加密发送(国密)

        //图片加水印

        //

        //数据库处理
        return mapper.restFlag1(pid) > 0;
    }
    @Override
    public boolean addOrder(String from,String to,String work_name,String receiptID,String recordId,String time,String status){
        System.out.println(from);
        System.out.println(to);
        System.out.println(work_name);
        System.out.println(receiptID);
        System.out.println(recordId);
        System.out.println(time);
        System.out.println(status);
        System.out.println(to);
        return mapper.creatOrder(from,to,work_name,receiptID,recordId,time,status) && mapper.insertfrom(from,recordId);
    }

    public boolean changeOrderstatus (String recordId,String status){
        return mapper.changeOrder(recordId,status);
    }

    @Override
    public List<Picture> getPictureList(String from, String to, String choice) {
        List<Picture> pictureList = new ArrayList<>();

        // 根据 choice 参数的值执行不同的逻辑
        if(choice.equals("1")){
            // 执行类型 1 的逻辑
            pictureList = mapper.findByTypeOne();
        } else if (choice.equals("2")) {
            // 执行类型 2 的逻辑
            pictureList = mapper.findByTypeTwo(from, to);
        }
        return pictureList;
    }
    @Override
    public List<String> getUrlList() {
        List<String> urlList = new ArrayList<>();

        urlList = mapper.findUrl();

        return urlList;
    }
    @Override
    public List<Picture> getPictureListByUsername(String from, String to, String username){
        List<Picture> pictureList2 = mapper.findByUsername(from, to, username);
        return pictureList2;
    }
    public List<String> getUrlListByUsername(String from, String to, String username){
        List<String> urlList2 = mapper.findUrlByUsername(username);
        return urlList2;
    }
    public List<Order> getOrderListByUsername(String from, String to, String username){
        List<Order> orderList = mapper.findOrderByUsername(username);
        return orderList;
    }
    @Override
    public List<Picture> getPictureList1(String user) {
        List<Picture> pictureList = new ArrayList<>();

        // 根据 choice 参数的值执行不同的逻辑
        pictureList = mapper.findMessage(user);

        return pictureList;
    }
    @Override
    public List<String> getUrlList1(String user) {
        List<String> urlList = new ArrayList<>();

        urlList = mapper.findUrl1(user);

        return urlList;
    }
}
