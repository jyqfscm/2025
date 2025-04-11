package com.example.service;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.transaction.model.dto.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FiscoServiceImpl  implements FiscoService {
    public final String configFile = "C:\\Users\\86187\\Desktop\\study_project\\study_project_backend\\src\\main\\resources\\config-example.toml";

    @Override
    public boolean one(String username, String imagebase, String pid,String time, String theme, String pid_marked, String markcode) throws Exception {
        System.out.println("in:");
        System.out.println(username);
        System.out.println(imagebase);
        System.out.println(pid);
        System.out.println(time);
        System.out.println(theme);
        System.out.println(pid_marked);
        System.out.println(markcode);

        Map<String, String> response = new HashMap<>();
        // 初始化BcosSDK对象
        BcosSDK sdk = BcosSDK.build(configFile);
        // 获取Client对象，此处传入的群组ID为1
        Client client = sdk.getClient(1);
        // 构造AssembleTransactionProcessor对象，需要传入client对象，CryptoKeyPair对象和abi、binary文件存放的路径。abi和binary文件需要在上一步复制到定义的文件夹中。
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "C:\\Users\\86187\\Desktop\\newnew\\study_project\\study_project_backend\\src\\main\\resources\\abi\\", "C:\\Users\\86187\\Desktop\\newnew\\study_project\\study_project_backend\\src\\main\\resources\\bin\\");//

        // 创建调用交易函数的参数，此处为传入一个参数
        List<Object> params = new ArrayList<>();
        params.add(pid);
        params.add(username);
        params.add(imagebase);
        params.add(time);
        params.add(theme);
        params.add(pid_marked);
        params.add(markcode);
        // 调用HelloWorld合约，合约地址为helloWorldAddress， 调用函数名为『set』，函数参数类型为params
        TransactionResponse transactionResponse = transactionProcessor.sendTransactionAndGetResponseByContractLoader("Record", "0x4f474c1516aab124cb1a3674d80cb861ba57c2d1", "addRecord", params);
        List<Object> returnvalues = transactionResponse.getReturnObject();
        if (returnvalues != null) {
            for (Object value : returnvalues) {
                System.out.println("上链返回值：" + value.toString());
            }
            return true;
        } else {
            System.out.println("error!");
            return false;
        }
    }

    @Override
    public String two(String username) throws Exception {
        // 初始化BcosSDK对象
        BcosSDK sdk = BcosSDK.build(configFile);
        // 获取Client对象，此处传入的群组ID为1
        Client client = sdk.getClient(1);
        // 构造AssembleTransactionProcessor对象，需要传入client对象，CryptoKeyPair对象和abi、binary文件存放的路径。abi和binary文件需要在上一步复制到定义的文件夹中。
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "C:\\Users\\86187\\Desktop\\newnew\\study_project\\study_project_backend\\src\\main\\resources\\abi\\", "C:\\Users\\86187\\Desktop\\newnew\\study_project\\study_project_backend\\src\\main\\resources\\bin\\");//

        List<Object> params2 = new ArrayList<>();
        params2.add(username);
        // 调用HelloWorld合约，合约地址为helloWorldAddress， 调用函数名为『set』，函数参数类型为params
        TransactionResponse transactionResponse2 = transactionProcessor.sendTransactionAndGetResponseByContractLoader("Record", "0x4f474c1516aab124cb1a3674d80cb861ba57c2d1", "getRecord", params2);
        List<Object> returnvalue2 = transactionResponse2.getReturnObject();
        if (returnvalue2 != null) {
            if (returnvalue2.size() == 6) {
                String description2 = (String) returnvalue2.get(0);
                String remark2 = (String) returnvalue2.get(1);
                String time2 = (String) returnvalue2.get(2);
                String theme2 = (String) returnvalue2.get(3);
                String pid_marked2 = (String) returnvalue2.get(4);
                String markcode = (String) returnvalue2.get(5);
                System.out.println("Description: " + description2);
                System.out.println("Remark: " + remark2);
                System.out.println("Time: " + time2);
                System.out.println("Theme: " + theme2);
                System.out.println("PID: " + pid_marked2);
                System.out.println("markcode: " + markcode);
                return description2 + "|" + remark2 + "|" + time2 + "|" + theme2 + "|" + pid_marked2+ "|" + markcode; // 使用 "|" 作为分隔符
            } else {
                System.out.println("返回值不正确");
            }
        }
        return null;
    }

    @Override
    public String three(String from, String to, String imagebase, String receiptID, String recordId) throws Exception {
        // 初始化BcosSDK对象
        BcosSDK sdk = BcosSDK.build(configFile);
        // 获取Client对象，此处传入的群组ID为1
        Client client = sdk.getClient(1);
        // 构造AssembleTransactionProcessor对象，需要传入client对象，CryptoKeyPair对象和abi、binary文件存放的路径。abi和binary文件需要在上一步复制到定义的文件夹中。
        CryptoKeyPair keyPair = client.getCryptoSuite().createKeyPair();
        AssembleTransactionProcessor transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(client, keyPair, "C:\\Users\\86187\\Desktop\\newnew\\study_project\\study_project_backend\\src\\main\\resources\\abi\\", "C:\\Users\\86187\\Desktop\\newnew\\study_project\\study_project_backend\\src\\main\\resources\\bin\\");//

        String sellerID = from;
        String buyerID = to;
        String itemName = imagebase;
        // 创建调用交易函数的参数，此处为传入一个参数
        List<Object> params3 = new ArrayList<>();
        params3.add(receiptID);
        params3.add(sellerID);
        params3.add(buyerID);
        params3.add(itemName);
        params3.add(recordId);
        System.out.println("receiptID: " + receiptID);
        System.out.println("sellerID: " + sellerID);
        System.out.println("buyerID: " + buyerID);
        System.out.println("itemName: " + itemName);
        System.out.println("recordId: " + recordId);
        // 调用HelloWorld合约，合约地址为helloWorldAddress， 调用函数名为『set』，函数参数类型为params
        TransactionResponse transactionResponse3 = transactionProcessor.sendTransactionAndGetResponseByContractLoader("Record", "0x4f474c1516aab124cb1a3674d80cb861ba57c2d1", "TransactionRecord", params3);
        List<Object> returnvalue3 = transactionResponse3.getReturnObject();
        if (returnvalue3 != null) {
            for (Object value : returnvalue3) {
                System.out.println("上链返回值：" + value.toString());
            }
        }
        return null;
    }
}
