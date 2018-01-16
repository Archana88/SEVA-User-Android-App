package com.example.archana.seva2;
import android.app.Application;

class GlobalClass extends Application {

    private static String tokenValue;
    private static String userName;
    private static String API_URL = "http://52.53.236.5:8000/api/";
    private static String FIREBASE_TOKEN;

    public static String getTokenValue() {

        return tokenValue;
    }

    public static void setTokenValue(String str) {

        tokenValue = str;
    }

    public static String getUserName(){

        return userName;
    }

    public static void setUserName(String str){

        userName = str;
    }

    public static String getApiUrl() {

        return API_URL;
    }

    public static String getFirebaseToken() {

        return FIREBASE_TOKEN;
    }

    public static void setFirebaseToken(String firebaseToken) {

        FIREBASE_TOKEN = firebaseToken;
    }
}