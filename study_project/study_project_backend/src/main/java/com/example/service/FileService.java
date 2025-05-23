package com.example.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 阿里云OSS文件上传
     * @param file
     * @return
     */
    String upload(MultipartFile file);
    boolean creatPicture(String pid);
}
