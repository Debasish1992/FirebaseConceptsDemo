package com.example.debasishkumardas.firebaseconceptsdemo.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Debasish Kumar Das on 5/24/2017.
 */
public class Constants {
    public static final String BASE_URL_FIREBASE = "https://fir-concepts-665a8.firebaseio.com/";
    public static final String CHILD_NODE_USER = "Users";

    /**
     * Get Alphanuemric Number
     * @return
     */
    public static String getChannelId(){
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }
}
