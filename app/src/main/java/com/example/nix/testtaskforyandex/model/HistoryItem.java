package com.example.nix.testtaskforyandex.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Nix on 12.11.2017.
 */

public class HistoryItem extends RealmObject {
    @Required
    private String mSource;
    @Required
    private String mResult;
    @Required
    private String mFCode;
    @Required
    private String mSCode;

    public HistoryItem() {
    }

    public HistoryItem(String source, String result, String FCode, String SCode) {
        mSource = source;
        mResult = result;
        mFCode = FCode;
        mSCode = SCode;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

    public String getFCode() {
        return mFCode;
    }

    public void setFCode(String FCode) {
        mFCode = FCode;
    }

    public String getSCode() {
        return mSCode;
    }

    public void setSCode(String SCode) {
        mSCode = SCode;
    }
}
