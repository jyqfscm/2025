package com.example.service;

import com.example.entity.Account;
import com.example.mapper.UserMapper;
import com.example.test.watermark;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.kms.v2.KmsClient;
import com.huaweicloud.sdk.kms.v2.model.EncryptDatakeyRequest;
import com.huaweicloud.sdk.kms.v2.model.EncryptDatakeyRequestBody;
import com.huaweicloud.sdk.kms.v2.model.EncryptDatakeyResponse;
import com.huaweicloud.sdk.kms.v2.region.KmsRegion;
import jakarta.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;


@Service
public class AuthorServiceImpl implements AuthorService {

    @Value("${spring.mail.username}")
    String from;
    @Resource
    UserMapper mapper;
    @Resource
    watermark watermark;
    @Resource
    MailSender mailSender;
    @Resource
    StringRedisTemplate template;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null)
            throw new UsernameNotFoundException("用户名不能为空");
        Account account = mapper.findAccountByNameOrEmail(username);
        if(account == null)
            throw new UsernameNotFoundException("用户名或者密码错误");

        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }
    @Override
    public String sendVaildateEmail(String email, String sessionId, boolean hasAccount){
        /*
        * 1. 先生成对应的验证码
        * 2. 把邮箱和对应的验证码直接放在Redis里面
        * 3. 发送验证码到指定邮箱
        * 4. 如果发送失败，把Redis里面的刚刚插入的删除
        * 5. 用户在注册时，再从Redis里面取出对应的键值对，看验证码是否一致
        * */
        String key = "email:" + sessionId + ":" + email + ":" + hasAccount;
        if(Boolean.TRUE.equals(template.hasKey(key))){
            Long expire = Optional.ofNullable(template.getExpire(key,TimeUnit.SECONDS)).orElse(0L);
            if(expire > 120) return "操作频繁，请稍后再试";
        }
        //重置密码
        if(hasAccount && mapper.findAccountByNameOrEmail(email) == null){
            return "没有该邮箱的用户";
        }
        //注册
        if(!hasAccount && mapper.findAccountByNameOrEmail(email) != null){
            return "邮箱已经被注册";
        }
        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("您的验证邮箱：" + email);
        message.setText("您的验证码：" + code);
        try {
            mailSender.send(message);
            template.opsForValue().set(key, String.valueOf(code),3, TimeUnit.MINUTES);
            return null;
        } catch (MailException e){
            e.printStackTrace();
            return "验证码发送失败，请稍后重试";
        }
    }

    @Override
    public String vaildateAndRegister(String username, String password, String email, String code, String publicKey, String sessionId){
        String key = "email:" + sessionId + ":" + email + ":false";
        if(Boolean.TRUE.equals(template.hasKey(key))){
            String s = template.opsForValue().get(key);
            if(s==null){
                return "验证码失效，请重新获取";
            }

            // 调用方法生成30位二进制数
            String markcode = generateDecimalFromUsername(username);

            // 输出生成的二进制数
            System.out.println("Username: " + username);
            System.out.println("30-bit Binary Sequence: " + markcode);
            if(s.equals(code)) {
                Account account = mapper.findAccountByNameOrEmail(username);
                if (account != null)
                    return "用户名已存在";
                password = encoder.encode(password);
                template.delete(key);
                if (mapper.creatAccount(username, password, email, publicKey, markcode) > 0) {
                    return null;
                } else
                    return "内部错误，请联系管理员";
            }else
                return "验证码错误，请输入正确验证码";

        }else
            return "请先获取验证码";

    }

    public static String generateDecimalFromUsername(String username) {
        try {
            // 使用SHA-256哈希算法
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(username.getBytes(StandardCharsets.UTF_8));

            // 将哈希值转换为正整数
            int hashInt = 0;
            for (int i = 0; i < 4; i++) {  // 取前4个字节来计算（4字节=32位）
                hashInt = (hashInt << 8) + (hash[i] & 0xFF);
            }

            // 使用取模运算确保数字在 0 到 2^22 之间
            int uniqueDecimal = Math.abs(hashInt) % 4194304;

            // 返回结果的字符串形式
            return String.valueOf(uniqueDecimal);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
    @Override
    public String vaildateOnly(String email, String code, String sessionId) {

        String key = "email:" + sessionId + ":" + email + ":true";
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            String s = template.opsForValue().get(key);
            if (s == null) {
                return "验证码失效，请重新获取";
            }
            if (s.equals(code)) {
                template.delete(key);
                return null;
            } else
                return "验证码错误，请输入正确验证码";

        } else
            return "请先获取验证码";
    }
    @Override
    public boolean resetPassword(String password, String email) {
        password = encoder.encode(password);
        return mapper.restPasswordByEmail(password, email) > 0;
    }

    @Override
    public String addBuyCar(String id, String name, String cname, String price) {
        if(mapper.addBuyCar(id,name,cname,price) > 0){
            return null;
        }else
            return "内部错误，请联系管理员";
    }


    @Override
    public String addOrder(String to,String sb,String username, String work_name, String theme, String time, String sb_marked){
        try {
            if(mapper.addOrder(to, sb,username, work_name, theme, time, sb_marked) > 0){
                return null;
            }else
                return "内部错误，请联系管理员";
        }catch (Exception e) {
            e.printStackTrace();
            return "内部错误，请联系管理员";
        }

    }

    @Override
    public String Addwatermark(String url1, String url2, String code, String choice, String filename) throws IOException, InterruptedException {
        if (watermark.Add(url1, url2, code, choice, filename) != null) {
            System.out.println("加水印成功");
            System.out.println("url1:" + url1);
            System.out.println("url2:" + url2);
            return "200";
        } else {
            System.out.println("加水印失败");
            return "内部错误，请联系管理员";
        }

    }

    public String Decode(String url2, String choice, String demarkcode, String ifmodified) throws IOException, InterruptedException {
        String result = watermark.decode(url2, choice, demarkcode, ifmodified);
        if (result != null) {
            System.out.println("解水印成功");
            System.out.println(result);
            return result;
        } else {
            System.out.println("加水印失败");
            return "内部错误，请联系管理员";
        }

    }

    @Override
    public String getarea(String requestBody) throws IOException, InterruptedException {
        String result = watermark.getarea(requestBody);
        if (result != null) {
            System.out.println("区域角点检测成功");
            System.out.println("regions:" + requestBody);
            return result;
        } else {
            System.out.println("区域传输失败");
            return "内部错误，请联系管理员";
        }
    }

    @Override
    public String Savepoint(String requestBody) throws IOException, InterruptedException {
        String result = watermark.Savepoints(requestBody);
        if (result != null) {
            System.out.println("水印解码成功");
            System.out.println("decode result:" + result);
            return result;
        } else {
            System.out.println("水印解码失败");
            return "内部错误，请联系管理员";
        }
    }
    @Override
    public EncryptDatakeyResponse callback(String DatakeyPlainLength, String PlainText, String KeyId) throws IOException, InterruptedException, NoSuchAlgorithmException {
        String ak = "HPUATMEMKDSWSKOF5ZN0";
        String sk = "3mmR5dxSgCNLcTrqP9h5x0tL8f2AhKnUse3rY8QC";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // 将输入字符串转换为字节数组（注意编码）
        byte[] hashBytes = digest.digest(PlainText.getBytes(java.nio.charset.StandardCharsets.UTF_8));

        // 将字节数组转换为十六进制字符串
        String hexHash = HexFormat.of().formatHex(hashBytes);
        PlainText = PlainText + hexHash;
        EncryptDatakeyResponse response = new EncryptDatakeyResponse();

        ICredential auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk);

        KmsClient client = KmsClient.newBuilder()
                .withCredential(auth)
                .withRegion(KmsRegion.valueOf("cn-north-4"))
                .build();
        EncryptDatakeyRequest request = new EncryptDatakeyRequest();
        EncryptDatakeyRequestBody body = new EncryptDatakeyRequestBody();
        body.withDatakeyPlainLength(DatakeyPlainLength);
        body.withPlainText(PlainText);
        body.withKeyId(KeyId);
        request.withBody(body);
        try {
            response = client.encryptDatakey(request);
            System.out.println(response);
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getRequestId());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }
        return response;
    }
}
