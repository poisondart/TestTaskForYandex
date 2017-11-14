package com.example.nix.testtaskforyandex.bottom_nav_fragments;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;
import com.example.nix.testtaskforyandex.R;
import com.example.nix.testtaskforyandex.model.LocalBDItem;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nix on 07.10.2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener{
    private Preference mClearHistory;
    private Preference mClearFavs;
    private Realm mRealm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getContext());
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_layout);
        mClearHistory = getPreferenceScreen().findPreference("pref_clear_history");
        mClearFavs = getPreferenceScreen().findPreference("pref_clear_fav");
        mClearHistory.setOnPreferenceClickListener(this);
        mClearFavs.setOnPreferenceClickListener(this);
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
        if(items != null){
            mRealm.beginTransaction();
            for (LocalBDItem item : items){
                if(item.isFavorite()){
                    item.setHistory(false);
                }else {
                    item.deleteFromRealm();
                }
            }
            mRealm.commitTransaction();
        }else{
            Toast.makeText(getContext(), R.string.history_empty, Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getContext(), R.string.toast_clear_history, Toast.LENGTH_LONG).show();
    }

    private void clearFavs(){
        RealmResults<LocalBDItem> items = mRealm.where(LocalBDItem.class)
                .equalTo(LocalBDItem.IS_FAVORITES, true).findAll();
        if(items != null){
            mRealm.beginTransaction();
            items.deleteAllFromRealm();
            mRealm.commitTransaction();
        }else{
            Toast.makeText(getContext(), R.string.favs_empty, Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getContext(), R.string.toast_clear_favs, Toast.LENGTH_LONG).show();
    }
}
