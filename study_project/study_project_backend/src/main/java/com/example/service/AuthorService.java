package com.example.service;

import com.huaweicloud.sdk.kms.v2.model.EncryptDatakeyResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface AuthorService  extends UserDetailsService {
    String sendVaildateEmail(String email, String sessionId, boolean hasAccount);

    String vaildateAndRegister(String username, String password, String email, String code, String publicKey, String sessionId);

    String vaildateOnly(String email, String code, String sessionId);

    boolean resetPassword(String password, String email);

    String addBuyCar(String id, String name, String cname, String price);

   String addOrder(String to,String sb, String username, String work_name, String theme, String time, String sb_marked);

    String Addwatermark(String url1,String url2,String code,String choice,String filename) throws IOException, InterruptedException;

    String Decode(String url2,String choice,String demarkcode,String ifmodified) throws IOException, InterruptedException;

    String getarea(String requestBody) throws IOException, InterruptedException;

    String Savepoint(String requestBody) throws IOException, InterruptedException;

    EncryptDatakeyResponse callback(String DatakeyPlainLength, String PlainText, String KeyId) throws IOException, InterruptedException, NoSuchAlgorithmException;
}
