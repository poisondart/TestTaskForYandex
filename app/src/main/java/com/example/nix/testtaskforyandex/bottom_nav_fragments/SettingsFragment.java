package com.example.nix.testtaskforyandex.bottom_nav_fragments;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import com.example.nix.testtaskforyandex.R;

/**
 * Created by Nix on 07.10.2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_layout);
    }
}
