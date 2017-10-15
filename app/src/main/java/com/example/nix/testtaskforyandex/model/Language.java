package com.example.nix.testtaskforyandex.model;



/**
 * Created by Nix on 12.10.2017.
 */

public class Language {
    private String mCode;
    private String mName;
    public Language(String name, String code) {
        mName = name;
        mCode = code;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
