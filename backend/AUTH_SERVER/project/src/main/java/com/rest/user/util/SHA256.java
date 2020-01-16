package com.rest.user.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    private static SHA256 instance;
    private SHA256() {}

    public static synchronized SHA256 getInstance(){
        if(instance == null){
            instance = new SHA256();
        }
        return instance;
    }


    public String encodeSHA256(String str) {
        String SHA = "";

        try {
            //암호화 방식 지정 메서드
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 256, 16).substring(1));
            }
            SHA = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            SHA = null;
        }

        return SHA;
    }
}