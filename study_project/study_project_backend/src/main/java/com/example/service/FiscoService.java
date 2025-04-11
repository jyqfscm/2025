package com.example.service;

public interface FiscoService {
    boolean one(String username, String imagebase, String pid, String time, String theme, String pid_marked, String markcode) throws Exception;

    String two(String username) throws Exception;

    String three(String from, String to, String imagebase,String receiptID, String recordId) throws Exception;
}
