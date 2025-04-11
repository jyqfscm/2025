package com.example.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class KeyPair {
    private String privateKey;
    private String publicKey;
    public synchronized void setKeyPair(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public synchronized String getPublicKey() {
        return publicKey;
    }

    public synchronized String getPrivateKey() {
        return privateKey;
    }
}
