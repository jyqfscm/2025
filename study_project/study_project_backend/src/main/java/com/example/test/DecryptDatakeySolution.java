package com.example.test;

import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.kms.v2.region.KmsRegion;
import com.huaweicloud.sdk.kms.v2.*;
import com.huaweicloud.sdk.kms.v2.model.*;


public class DecryptDatakeySolution {

    public static void main(String[] args) {
        // The AK and SK used for authentication are hard-coded or stored in plaintext, which has great security risks. It is recommended that the AK and SK be stored in ciphertext in configuration files or environment variables and decrypted during use to ensure security.
        // In this example, AK and SK are stored in environment variables for authentication. Before running this example, set environment variables CLOUD_SDK_AK and CLOUD_SDK_SK in the local environment
        String ak = "HPUATMEMKDSWSKOF5ZN0";
        String sk = "3mmR5dxSgCNLcTrqP9h5x0tL8f2AhKnUse3rY8QC";

        ICredential auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk);

        KmsClient client = KmsClient.newBuilder()
                .withCredential(auth)
                .withRegion(KmsRegion.valueOf("cn-north-4"))
                .build();
        DecryptDatakeyRequest request = new DecryptDatakeyRequest();
        DecryptDatakeyRequestBody body = new DecryptDatakeyRequestBody();
        body.withDatakeyCipherLength("1");
        body.withCipherText("050045008b060c092f781390021d057c4f3110561c10f955f0abb1af3bedb9c0de38663334316430622d303239342d346132612d383632302d38613631393961656664363300000000232221201f1e1d1c1b1a191817161514131211100f0e0d0c0b0a090807060504030201cf2e58eef9ad4f922bbf4e3cf2d11fe0596891c39879049a3dc51b5fe6b783c7");
        body.withKeyId("8f341d0b-0294-4a2a-8620-8a6199aefd63");
        request.withBody(body);
        try {
            DecryptDatakeyResponse response = client.decryptDatakey(request);
            System.out.println(response.toString());
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
    }
}