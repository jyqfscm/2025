package com.example.entity;

import lombok.Data;


@Data
public class Order {
    Integer id;
    String from;
    String to;
    String work_name;

    String receiptID;
    String recordId;
    String time;
    String status;

}
