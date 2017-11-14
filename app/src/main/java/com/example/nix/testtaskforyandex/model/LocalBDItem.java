package com.example.nix.testtaskforyandex.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Nix on 12.11.2017.
 */

public class LocalBDItem extends RealmObject {

    /**/
    public static final String IS_HISTORY= "mIsHistory";
    public static final String IS_FAVORITES = "mIsFavorite";
    public static final String SOURCE_PROPERTY = "mSource";
    /**/

    @Required
    private String mSource;
    @Required
    private String mResult;
    @Required
    private String mFCode;
    @Required
    private String mSCode;

    private boolean mIsHistory = true;
    private boolean mIsFavorite = false;


    public LocalBDItem() {

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

    public boolean isHistory() {
        return mIsHistory;
    }

    public void setHistory(boolean history) {
        mIsHistory = history;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setFavorite(boolean favorite) {
        mIsFavorite = favorite;
    }
}
