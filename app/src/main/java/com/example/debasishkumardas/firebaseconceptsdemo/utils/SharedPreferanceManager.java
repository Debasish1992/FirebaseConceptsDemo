/*
package com.example.debasishkumardas.firebaseconceptsdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

*/
/**
 * Created by Debasish Kumar Das on 5/25/2017.
 *//*

public class SharedPreferanceManager {
    private static final String APP_SETTINGS = "APP_SETTINGS";
    private static final String USERNAME_LOGGEDIN = "Username";
    private static final String USERID_LOGGEDIN = "Userid";
    private static final String USERHOBBY_LOGGEDIN = "UserHobby";
    private static final String USEREMAIL_LOGGEDIN = "UserEmail";
    private static final String USERABOUTME_LOGGEDIN = "UserAboutMe";
    private static final String PREFERANCE_NAME = "UserPreferance";

    Context mContext;

    private static SharedPreferanceManager mInstance = null;
    SharedPreferences client;

    private SharedPreferanceManager(Context context){
        client = context.getSharedPreferences(PREFERANCE_NAME, context.MODE_PRIVATE);
    }

    public static SharedPreferanceManager getInstance(){
        if(mInstance == null) {
            mInstance = new SharedPreferanceManager();
        }
        return mInstance;
    }

    public SharedPreferences getClient(){
        return client;
    }

    public static void setUserValue(Context context,
                                          String userName,
                                          String userId,
                                          String userHobby,
                                          String userEmail,
                                          String userAboutMe) {
        final SharedPreferences.Editor editor = context.getSharedPreferences(context).edit();
        editor.putString(USERNAME_LOGGEDIN , userName);
        editor.putString(USERID_LOGGEDIN , userId);
        editor.putString(USERHOBBY_LOGGEDIN , userHobby);
        editor.putString(USEREMAIL_LOGGEDIN , userEmail);
        editor.putString(USERABOUTME_LOGGEDIN , userAboutMe);
        editor.commit();
    }

    public static String getUserEmail(Context context){
        return getSharedPreferences(context).getString(USEREMAIL_LOGGEDIN, "");
    }

    public static String getUserId(Context context){
        return getSharedPreferences(context).getString(USERID_LOGGEDIN, "");
    }

    public static String getUserName(Context context){
        return getSharedPreferences(context).getString(USERNAME_LOGGEDIN, "");
    }

    public static String getUserHobby(Context context){
        return getSharedPreferences(context).getString(USERHOBBY_LOGGEDIN, "");
    }

    public static String getUserAboutme(Context context){
        return getSharedPreferences(context).getString(USERABOUTME_LOGGEDIN, "");
    }

}
*/
