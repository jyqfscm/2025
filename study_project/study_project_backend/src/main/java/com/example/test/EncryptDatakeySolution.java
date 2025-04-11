package com.example.test;

import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.kms.v2.region.KmsRegion;
import com.huaweicloud.sdk.kms.v2.*;
import com.huaweicloud.sdk.kms.v2.model.*;


public class EncryptDatakeySolution {

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
        EncryptDatakeyRequest request = new EncryptDatakeyRequest();
        EncryptDatakeyRequestBody body = new EncryptDatakeyRequestBody();
        body.withDatakeyPlainLength("64");
        body.withPlainText("7549d9aea901767bf3c0b3e14b10722eaf6f59053bbd82045d04e075e809a0fe6ccab48f8e5efe74e4b18ff0512525e527b10331100f357bf42125d8d5ced94ffbc8ac72b0785ca7fe33eb6776ce3990b11e32b299d9c0a9ee0305fb9540f797");
        body.withKeyId("8f341d0b-0294-4a2a-8620-8a6199aefd63");
        request.withBody(body);
        try {
            EncryptDatakeyResponse response = client.encryptDatakey(request);
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
