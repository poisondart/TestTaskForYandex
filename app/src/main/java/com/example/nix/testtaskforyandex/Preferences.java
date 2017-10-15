package com.example.nix.testtaskforyandex;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nix on 15.10.2017.
 */

public class Preferences {
    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sEditor;
    private static final String PREF_TITLE = "yandex_test";
    public static void initialize(Context context){
        sSharedPreferences = context.getSharedPreferences(PREF_TITLE, Context.MODE_PRIVATE);
    }
    public static String getFirstLanguage(Context context){
        return sSharedPreferences.getString(context.getString(R.string.f_lang), "русский");
    }
    public static String getSecondLanguage(Context context){
        return sSharedPreferences.getString(context.getString(R.string.s_lang), "английский");
    }
    public static String getFirstLanguageCode(Context context){
        return sSharedPreferences.getString(context.getString(R.string.f_lang_code), "ru");
    }
    public static String getSecondLanguageCode(Context context){
        return sSharedPreferences.getString(context.getString(R.string.s_lang_code), "en");
    }
    public static void setFirstLanguage(Context context, String lang){
        sEditor = sSharedPreferences.edit();
        sEditor.putString(context.getString(R.string.f_lang), lang);
        sEditor.apply();
    }
    public static void setSecondLanguage(Context context, String lang){
        sEditor = sSharedPreferences.edit();
        sEditor.putString(context.getString(R.string.s_lang), lang);
        sEditor.apply();
    }
    public static void setFirstLanguageCode(Context context, String lang){
        sEditor = sSharedPreferences.edit();
        sEditor.putString(context.getString(R.string.f_lang_code), lang);
        sEditor.apply();
    }
    public static void setSecondLanguageCode(Context context, String lang){
        sEditor = sSharedPreferences.edit();
        sEditor.putString(context.getString(R.string.s_lang_code), lang);
        sEditor.apply();
    }
    public static void switchLangs(Context context){
        String misc_name = getFirstLanguage(context);
        String misc_code = getFirstLanguageCode(context);
        setFirstLanguage(context, getSecondLanguage(context));
        setFirstLanguageCode(context, getSecondLanguageCode(context));
        setSecondLanguage(context, misc_name);
        setSecondLanguageCode(context, misc_code);
    }
}
