package com.example.nix.testtaskforyandex.bottom_nav_fragments;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;
import com.example.nix.testtaskforyandex.R;
import com.example.nix.testtaskforyandex.model.HistoryItem;
import io.realm.Realm;

/**
 * Created by Nix on 07.10.2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener{
    private Preference mClearHistory;
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
        mClearHistory.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){
            case "pref_clear_history":
                mRealm.beginTransaction();
                mRealm.delete(HistoryItem.class);
                mRealm.commitTransaction();
                Toast.makeText(getContext(), R.string.toast_clear_history, Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRealm.close();
    }
}
