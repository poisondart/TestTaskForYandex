package com.example.nix.testtaskforyandex.bottom_nav_fragments;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;
import com.example.nix.testtaskforyandex.R;
import com.example.nix.testtaskforyandex.model.LocalBDItem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nix on 07.10.2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener{
    private Preference mClearHistory;
    private Preference mClearFavs;
    private Preference mShowLicenses;
    private Realm mRealm;
    private String mMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getContext());
        mRealm = Realm.getDefaultInstance();
        initLicenseMessage();

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_layout);
        mClearHistory = getPreferenceScreen().findPreference("pref_clear_history");
        mClearFavs = getPreferenceScreen().findPreference("pref_clear_fav");
        mShowLicenses = getPreferenceScreen().findPreference("license");
        mClearHistory.setOnPreferenceClickListener(this);
        mClearFavs.setOnPreferenceClickListener(this);
        mShowLicenses.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){
            case "pref_clear_history":
                clearHistory();
                break;
            case "pref_clear_fav":
                clearFavs();
                break;
            case "license":
                showLicenses();
                break;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRealm.close();
    }

    private void clearHistory(){
        RealmResults<LocalBDItem> items = mRealm.where(LocalBDItem.class).findAll();
        if(!items.isEmpty()){
            mRealm.beginTransaction();
            for (LocalBDItem item : items){
                if(item.isFavorite()){
                    item.setHistory(false);
                }else {
                    item.deleteFromRealm();
                }
            }
            mRealm.commitTransaction();
            Toast.makeText(getContext(), R.string.toast_clear_history, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(), R.string.history_empty, Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFavs(){
        RealmResults<LocalBDItem> items = mRealm.where(LocalBDItem.class)
                .equalTo(LocalBDItem.IS_FAVORITES, true).findAll();
        if(!items.isEmpty()){
            mRealm.beginTransaction();
            items.deleteAllFromRealm();
            mRealm.commitTransaction();
            Toast.makeText(getContext(), R.string.toast_clear_favs, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(), R.string.favs_empty, Toast.LENGTH_SHORT).show();
        }
    }
    private void initLicenseMessage(){
        try{
            AssetManager assetManager = getContext().getAssets();
            InputStream inputStream = assetManager.open("licenses.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString;
            StringBuilder stringBuilder = new StringBuilder();

            while ( (receiveString = bufferedReader.readLine()) != null ) {
                if(receiveString.equals("")){
                    stringBuilder.append(System.getProperty("line.separator"));
                }else{
                    stringBuilder.append(receiveString);
                }
            }
            inputStream.close();
            mMessage = stringBuilder.toString();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void showLicenses(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.license)
                .setMessage(mMessage)
                .setCancelable(false)
                .setPositiveButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
