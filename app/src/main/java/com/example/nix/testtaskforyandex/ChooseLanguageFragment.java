package com.example.nix.testtaskforyandex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Nix on 12.10.2017.
 */

public class ChooseLanguageFragment extends Fragment {
    private Toolbar mToolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.choose_lang_fragment, container, false);
        mToolbar = (Toolbar)v.findViewById(R.id.choose_lg_toolbar);
        mToolbar.setTitle("Выбор языка");
        return v;
    }
}
