package com.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class Order_me{
    String username;
    String work_name;

    List<Integer> theme; // 你可能需要更具体的字段，这里暂时使用 buyer_id
    String time;


}
