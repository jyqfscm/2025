package com.example.controller;

import com.example.entity.Order_me;
import com.example.entity.ResponseResult;
import com.example.entity.RestBean;
import com.example.service.AuthorService;
import com.example.service.FileService;
import com.example.test.AESUtil;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.kms.v2.KmsClient;
import com.huaweicloud.sdk.kms.v2.model.EncryptDatakeyRequest;
import com.huaweicloud.sdk.kms.v2.model.EncryptDatakeyRequestBody;
import com.huaweicloud.sdk.kms.v2.model.EncryptDatakeyResponse;
import com.huaweicloud.sdk.kms.v2.region.KmsRegion;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@Validated
@RestController
@CrossOrigin
@RequestMapping("/api/file")
public class FileUploadController {
    @Autowired
    private FileService fileService;

    public String sb;
    public String sb_marked;

    private static final String IMAGE_DIRECTORY = "C:/Users/86187/Desktop/PIMoG-Improveded-master/image/concatenate_output/";
    private String globalurl;
    @Resource
    AuthorService service;

//    @PostMapping("/getpath")
//    public ResponseResult getPath(@RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
//        System.out.println("ssssssssss");
//        // 判断文件是否为空
//        if(file.isEmpty()){
//            return ResponseResult.fail();
//        }
//        // 获取传过来的文件名字
//        String OriginalFilename=file.getOriginalFilename();
//        //System.out.println("：" + OriginalFilename);
//        // 为了防止重名覆盖，获取系统时间戳+原始文件的后缀名
//        String fileName=System.currentTimeMillis()+"."+OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);
//        // 设置保存地址（这里是转义字符）
//        //1.后台保存位置
//        String path = "D:/images/images/";
//        globalurl = path+fileName;
//        File dest=new File(globalurl);
//        // 判断文件是否存在
//        if(!dest.getParentFile().exists()){
//            // 不存在就创建一个
//            dest.getParentFile().mkdirs();
//        }
//        file.transferTo(dest);
//
//        return new ResponseResult(200, "文件创建成功", "fileName");
//    }


    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadImage(@RequestParam("pathvalue") String pathValue, @RequestParam("filename") String fileName) throws IOException {
        System.out.println("Path: " + pathValue);
        System.out.println("File name: " + fileName);
        Path imagePath = Paths.get(pathValue, fileName);
        byte[] imageBytes = Files.readAllBytes(imagePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }


    @PostMapping("/getpath")
    public ResponseResult getPath(@RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        // 判断文件是否为空
        if(file.isEmpty()){
            return ResponseResult.fail();
        }
        // 获取传过来的文件名字
        String OriginalFilename=file.getOriginalFilename();
        System.out.println("：" + OriginalFilename);
        // 为了防止重名覆盖，获取系统时间戳+原始文件的后缀名
        String fileName=System.currentTimeMillis()+"."+OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);
        // 设置保存地址（这里是转义字符）
        //1.后台保存位置
        String path = "C:/Users/86187/Desktop/PIMoG-Improveded-master/image/crop_input/";
        globalurl = path+fileName;
        File dest=new File(globalurl);
        // 判断文件是否存在
        if(!dest.getParentFile().exists()){
            // 不存在就创建一个
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);

        return new ResponseResult(200, "文件创建成功", fileName);
    }
    @PostMapping("/getdecodepath")
    public ResponseResult getdecodePath(@RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        // 判断文件是否为空
        if(file.isEmpty()){
            return ResponseResult.fail();
        }
        // 获取传过来的文件名字
        String OriginalFilename=file.getOriginalFilename();
        //System.out.println("：" + OriginalFilename);
        // 为了防止重名覆盖，获取系统时间戳+原始文件的后缀名
        String fileName=System.currentTimeMillis()+"."+OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);
        // 设置保存地址（这里是转义字符）
        //1.后台保存位置
        String path = "C:/Users/86187/Desktop/Screen-Shooting-Resilient-Watermarking-main/";
        globalurl = path+"temp_image.png";
        File dest=new File(globalurl);
        // 判断文件是否存在
        if(!dest.getParentFile().exists()){
            // 不存在就创建一个
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);

        return new ResponseResult(200, "文件创建成功", fileName);
    }



    @PostMapping("/upload")
    public RestBean<String> Upload(@RequestParam("pathvalue")String pathvalue,
                                   @RequestParam("markcode")String markcode,
                                   @RequestParam("filename")String filename,
                                   HttpSession session){
        String url1 = pathvalue;
        String code = markcode;
        String url2 = globalurl;
        String choice = "encode";

        File dest = new File(url1);
        System.out.println(url1);
        System.out.println(code);
        System.out.println(filename);
        System.out.println(url2);

        // 判断文件是否存在
        if(!dest.getParentFile().exists()){
            // 不存在就创建一个
            dest.getParentFile().mkdirs();
            System.out.println("成功");
        }
        try {
            if (service.Addwatermark(url1,url2,code,choice,filename).equals("200"))
                return RestBean.success("嵌入水印成功");
            else{
                return RestBean.failure(400,"表单错误");
            }

        }catch (Exception e){
            e.printStackTrace();
            return RestBean.failure(400,"表单错误");
        }

    }


    @PostMapping("/decodeupload")
    public RestBean<String> DecodeUpload(@RequestParam("pathvalue")String pathvalue,
                                         @RequestParam("demarkcode")String demarkcode,
                                         @RequestParam("ifmodified")String ifmodified,
                                         HttpSession session){
        String url2 = globalurl;
        String choice = "decode";

        System.out.println(url2);

        try {
            String result = service.Decode(url2,choice,demarkcode,ifmodified);
            if (result!=null)
                return RestBean.success(result); // 将 result 作为数据部分返回
            else {
                return RestBean.failure(400, "表单错误"); // 将 result 作为数据部分返回
            }

        }catch (Exception e){
            e.printStackTrace();
            return RestBean.failure(400,"表单错误");
        }

    }

    @PostMapping("/getarea")
    public RestBean<String> Getarea(@RequestParam("regionvalue") String requestBody) {

        try {

            // 处理 regions
            System.out.println("Received regions: " + requestBody);
            String result = service.getarea(requestBody);
            if (result!=null)
                return RestBean.success(result); // 将 result 作为数据部分返回
            else {
                return RestBean.failure(400, "表单错误"); // 将 result 作为数据部分返回
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(400, null); // 返回 null 作为数据部分
        }
    }
    @PostMapping("/savepoints")
    public RestBean<String> Savepoints(@RequestParam("savePoints") String requestBody) {

        try {

            // 处理 regions
            System.out.println("Received points: " + requestBody);
            String result = service.Savepoint(requestBody);
            if (result!=null)
                return RestBean.success(result); // 将 result 作为数据部分返回
            else {
                return RestBean.failure(400, "表单错误"); // 将 result 作为数据部分返回
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(400, null); // 返回 null 作为数据部分
        }
    }







    /**
     * 文件上传接口
     * @param file
     * @return
     */
    @PostMapping("/uploadoss")
    public RestBean<String> upload(@RequestPart("file") MultipartFile file) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //解密

        //图片加水印

        String imgFileStr = fileService.upload(file);
        sb = imgFileStr;
        System.out.println(imgFileStr);
        //编码变成唯一标号pid
//        String pid = AESUtil.encrypt(imgFileStr);
//        System.out.println("pid:" + pid);
        //存入数据库
        if(fileService.creatPicture(imgFileStr)){
            if(imgFileStr == null || "".equals(imgFileStr)){
                return RestBean.failure(400,"上传阿里云失败");
            }else{
                return RestBean.success("成功上传");
            }
        }else{
            return RestBean.failure(400,"上传阿里云失败");
        }

    }

    /**
     * 水印图片上传接口
     * @param file
     * @return
     */
    @PostMapping("/uploadossmarked")
    public RestBean<String> uploadmarked(@RequestPart("file") MultipartFile file) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //解密

        //图片加水印

        String imgFileStr = fileService.upload(file);
        sb_marked = imgFileStr;
        System.out.println(imgFileStr);
        //编码变成唯一标号pid
//        String pid = AESUtil.encrypt(imgFileStr);
//        System.out.println("pid:" + pid);
        //存入数据库
//        if(fileService.creatPicture(imgFileStr)){
//            if(imgFileStr == null || "".equals(imgFileStr)){
//                return RestBean.failure(400,"上传阿里云失败");
//            }else{
//                return RestBean.success("成功上传");
//            }
//        }else{
//            return RestBean.failure(400,"上传阿里云失败");
//        }
        return RestBean.success("成功上传");

    }
    @PostMapping("/addorder")
    public RestBean<String> addorder(@RequestParam("to")String to,
                                     @RequestParam("username")String username,
                                     @RequestParam("work_name")String work_name,
                                     @RequestParam("theme")String theme,
                                     @RequestParam("time")String time) {
        System.out.println(theme);

        try {

            System.out.println(sb);
            System.out.println(sb_marked);
            String message = sb + "|" + sb_marked;
            if (service.addOrder(to,sb,username,work_name,theme,time,sb_marked) == null)
                return RestBean.success(message);
            else
                return RestBean.failure(400, "添加订单失败1，请联系管理员");

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(400, "添加订单失败2，请联系管理员");
        }
    }

    @PostMapping("/callback")
    public RestBean<String> callBack(@RequestParam("callback")String callback
                                         ){
        String back = callback;
        try {
            String DatakeyPlainLength = Integer.toString(back.length()/2);
            String KeyId = "8f341d0b-0294-4a2a-8620-8a6199aefd63";
            EncryptDatakeyResponse message = service.callback(DatakeyPlainLength,back,KeyId);
            if (message != null){
                return RestBean.success(message.getCipherText());
            }

            else
                return RestBean.failure(400,"获取失败");

        } catch (Exception e) {
            e.printStackTrace();
            return RestBean.failure(400,"传输故障");
        }
    }
    /*public ResponseResult uploadFile(@RequestParam(value = "file",required = false) MultipartFile file){
        // 判断文件是否为空
        if(file.isEmpty()){
            return ResponseResult.fail();
        }
        // 获取传过来的文件名字
        String OriginalFilename=file.getOriginalFilename();
        //System.out.println("：" + OriginalFilename);
        // 为了防止重名覆盖，获取系统时间戳+原始文件的后缀名
        String fileName=System.currentTimeMillis()+"."+OriginalFilename.substring(OriginalFilename.lastIndexOf(".")+1);
        // 设置保存地址（这里是转义字符）
        //1.后台保存位置
        String path = "D:/images/images/";
        String url1 = path+fileName;
        String url2 = "D:/images/images/1.png";
        File dest=new File(url1);
        // 判断文件是否存在
        if(!dest.getParentFile().exists()){
            // 不存在就创建一个
            dest.getParentFile().mkdirs();
        }
        try {
            // 后台上传
            file.transferTo(dest);
            if (service.Addwatermark(url1,url2) == "200")
                return new ResponseResult(200, "文件上传成功", fileName);
            else{
                return ResponseResult.fail();
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.fail();
        }


    }*/

}
