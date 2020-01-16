package com.rest.user.util;


import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class TempKey {

    private boolean lowerCheck;
    private int size;

    public String getKey(String email) {

        return init(email);
    }

    private String init(String email) {
        /*
        Random ran = new Random();
        StringBuffer sb = new StringBuffer();
        int num = 0;
        do {
            num = ran.nextInt(75)+48;
            if((num>=48 && num<=57) || (num>=65 && num<=90) || (num>=97 && num<=122)) {
                sb.append((char)num);
            }else {
                continue;
            }
        } while (sb.length() < size);
        if(lowerCheck) {
            return sb.toString().toLowerCase();
        }
        return sb.toString();
    }
         */


        String uuid = email + "." + UUID.randomUUID().toString();
        return uuid;
   }

}