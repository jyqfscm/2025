package com.example.test;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class test {

    public Map<String, String> generateKeyPair() throws IOException, InterruptedException {
        // Python的环境路径
        String exe = "C:\\Users\\86187\\AppData\\Local\\Programs\\Python\\Python311\\python.exe";
        // 存储Python脚本的路径
        String py = "C:/Users/86187/Desktop/Screen-Shooting-Resilient-Watermarking-main/SM2.py";
        // 定义传入Python脚本的命令行参数，将参数放入字符串数组里
        String[] cmdArr = new String[]{exe, py, "1", "", "", "", ""};

        // 使用Runtime.getRuntime()方法调用Python程序
        Process process = Runtime.getRuntime().exec(cmdArr);

        // 取得命令结果的输出流
        InputStream is = process.getInputStream();

        // 声明输出流对象，将结果显示出来
        DataInputStream dis = new DataInputStream(is);
        String output = dis.readLine();

        process.waitFor();

        // 解析输出字符串以获取私钥和公钥
        Map<String, String> keyPair = parseOutput(output);

        return keyPair;
    }

    private Map<String, String> parseOutput(String output) {
        Map<String, String> keyPair = new HashMap<>();
        // 使用正则表达式提取私钥和公钥
        Pattern pattern = Pattern.compile("privateKey:(.*?)publicKey:([0-9a-fA-F]+)");
        Matcher matcher = pattern.matcher(output);

        if (matcher.find()) {
            String privateKey = matcher.group(1).trim();
            String publicKey = matcher.group(2).trim();
            keyPair.put("privateKey", privateKey);
            keyPair.put("publicKey", publicKey);
        }

        return keyPair;
    }
}
