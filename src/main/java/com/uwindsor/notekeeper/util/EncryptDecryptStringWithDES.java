package com.uwindsor.notekeeper.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecryptStringWithDES {

    public static String encrypt(String content, String password) {
        try{
            Key aesKey = new SecretKeySpec(Arrays.copyOf(password.getBytes(StandardCharsets.UTF_8),   16), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(content.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String content, String password) {
        try {
            Key aesKey = new SecretKeySpec(Arrays.copyOf(password.getBytes(StandardCharsets.UTF_8),   16), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(cipher.doFinal(encrypted));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
