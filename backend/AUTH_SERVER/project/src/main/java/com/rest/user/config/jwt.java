package com.rest.user.config;

public class jwt {
    public static String ISSUER = "Myeong";
    public static String SECRET = "jwtSecretKey!";

    public static String getISSUER() { return ISSUER; }
    public static String getSECRET() { return SECRET; }
}
