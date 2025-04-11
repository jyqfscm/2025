package com.example.test;

import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.ECKeyUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.PlainDSAEncoding;
import org.springframework.stereotype.Component;

import java.io.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SM2Util {

    //加密
    public static String encrypt(String data, String publicKey){
        String publicKeyTmp = publicKey;
        if (publicKey.length() == 130) {
            //这里需要去掉开始第一个字节 第一个字节表示标记
            publicKeyTmp = publicKey.substring(2);
        }
        String xhex = publicKeyTmp.substring(0, 64);
        String yhex = publicKeyTmp.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        //创建sm2 对象
        SM2 sm2 = new SM2(null, ecPublicKeyParameters);
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        String hex = sm2.encryptHex(data, KeyType.PublicKey);
        return hex;

    }

    //解密
    public static String decrypt(String data, String privateKey){
        SM2 sm2 = new SM2(ECKeyUtil.toSm2PrivateParams(privateKey), null);
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        sm2.setEncoding(new PlainDSAEncoding());
        String encryptStr = sm2.decryptStr(data, KeyType.PrivateKey);
        return encryptStr;
    }

    // 签名
    public static String sign(String data, String privateKey) {
        ECPrivateKeyParameters privateKeyParams = ECKeyUtil.toSm2PrivateParams(privateKey);
        SM2 sm2 = new SM2(null, privateKeyParams.toString());
        return sm2.signHex(data);
    }


    public static boolean verify(String data, String publicKey, String signData) throws IOException, InterruptedException {
        // Python的环境路径
        String exe = "C:\\Users\\86187\\AppData\\Local\\Programs\\Python\\Python311\\python.exe";
        // 存储Python脚本的路径
        String py = "C:\\Users\\86187\\Desktop\\study_project\\SM2.py";
        // 定义传入Python脚本的命令行参数，将参数放入字符串数组里
        String[] cmdArr = new String[]{exe, py, "4", data, "", publicKey, signData};

        // 使用Runtime.getRuntime()方法调用Python程序
        Process process = Runtime.getRuntime().exec(cmdArr);

        // 取得命令结果的输出流
        InputStream is = process.getInputStream();

        // 使用BufferedReader逐行读取输出流
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder outputBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            outputBuilder.append(line).append("\n");
        }

        // 等待命令执行完毕
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            // 执行失败，打印错误信息
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }
            return false;
        }

        // 解析输出字符串以获取验证结果
        String verify = parseOutputVerify(outputBuilder.toString());

        // 将字符串转换为布尔值
        return Boolean.parseBoolean(verify);
    }

    private static String parseOutputVerify(String output) {
        System.out.println(output);
        // 使用正则表达式提取验证结果
        Pattern pattern = Pattern.compile("verify:(True|False)");
        Matcher matcher = pattern.matcher(output);

        String verify = null;
        if (matcher.find()) {
            verify = matcher.group(1).trim();
        }
        return verify;
    }

}
