package com.bba.orents.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by USER on 6/27/2016.
 */
public class SharedPrefUtil {
    private Context context;
    private SharedPreferences sharedPreferences;
    private static SharedPrefUtil instance;
    private SharedPrefUtil(Context context){
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPrefUtil getInstance(Context context){
        if(instance == null){
            return new SharedPrefUtil(context);
        }
        return instance;
    }

    public void setStringPref(String key,String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public String getStringPref(String key,String defValue){
        return sharedPreferences.getString(key,defValue);
    }
}
