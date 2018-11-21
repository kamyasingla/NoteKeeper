package com.uwindsor.notekeeper.util;

import java.util.Base64;

public class SimpleEncryptDecrypt {

    public static String encrypt(String content, String password) {
        String encodeContent = Base64.getEncoder().encodeToString(content.getBytes());
        String reverse = new StringBuffer(encodeContent)
                .reverse()
                .toString();
        StringBuilder stringBuilder = new StringBuilder();
        int offset = password.length() / 2;
        for (int i = 0; i < reverse.length(); i++) {
            stringBuilder.append((char)(reverse.charAt(i) + offset));
        }
        return stringBuilder.toString();
    }

    public static String decrypt(String content, String password) {
        StringBuilder tmp = new StringBuilder();
        int offset = password.length() / 2;
        for (int i = 0; i < content.length(); i++) {
            tmp.append((char)(content.charAt(i) - offset));
        }
        String reversed = new StringBuffer(tmp.toString())
                .reverse()
                .toString();
        return new String(Base64.getDecoder().decode(reversed));
    }
}
