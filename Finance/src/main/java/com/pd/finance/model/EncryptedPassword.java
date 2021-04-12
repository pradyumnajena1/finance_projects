package com.pd.finance.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pd.finance.utils.CommonUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class EncryptedPassword {
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(EncryptedPassword.class);

    private String encryptedPassword;
    private String salt;


    public EncryptedPassword(String password) {

        try {
            byte[] saltBytes = getSaltBytes();
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_16);
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(saltBytes);
            byte[] digestBytes = md.digest(passwordBytes);

            this.encryptedPassword = CommonUtils.getHexString(digestBytes);
            this.salt = CommonUtils.getHexString(saltBytes);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }




    public EncryptedPassword(String encryptedPassword, String salt) {
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;
    }

    static private byte[] getSaltBytes() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getSalt() {
        return salt;
    }
    public boolean validatePassword(String password){

        boolean result = false;
        try {
            byte[] saltBytes = CommonUtils.hexStringToByteArray(salt);
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_16);
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(saltBytes);
            byte[] hashBytes = md.digest(passwordBytes);
            String encryptedPassword = CommonUtils.getHexString(hashBytes);
            result= encryptedPassword.equals(this.encryptedPassword);

        } catch ( Exception e) {
            logger.error(e.getMessage(),e);
        }
        return result;
    }
}
