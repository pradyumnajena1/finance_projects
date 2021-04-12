package com.pd.finance.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptedPassword {

    private String encryptedPassword;
    private String salt;

    public EncryptedPassword(String password) {
        MessageDigest md = null;
        byte[] salt = getSaltBytes();
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            this.encryptedPassword = new String(hashedPassword);
            this.salt = new String(salt);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    protected byte[] getSaltBytes() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getSalt() {
        return salt;
    }
}
