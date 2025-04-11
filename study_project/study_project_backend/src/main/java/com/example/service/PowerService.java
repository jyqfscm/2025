package com.example.service;

import com.example.entity.Order;
import com.example.entity.Picture;

import java.util.List;

public interface PowerService {
    boolean Trade(String pid,String username);

    boolean addOrder(String from,String to,String work_name,String receiptID,String recordId,String time,String status);

    boolean changeOrderstatus (String recordId,String status);

    List<String> getUrlList();

    List<Picture> getPictureList(String from, String to, String choice);

    List<Picture> getPictureListByUsername(String from, String to, String username);

    List<String> getUrlListByUsername(String from, String to, String username);

    List<Order> getOrderListByUsername(String from, String to, String username);

    List<Picture> getPictureList1(String user);
    List<String> getUrlList1(String user);
}
