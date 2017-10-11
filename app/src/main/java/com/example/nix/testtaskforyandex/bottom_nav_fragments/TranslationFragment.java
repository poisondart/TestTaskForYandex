package com.example.nix.testtaskforyandex.bottom_nav_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.nix.testtaskforyandex.R;

/**
 * Created by Nix on 06.10.2017.
 */

public class TranslationFragment extends Fragment {
    private Spinner mFirstLanguageSp, mSecondLanguageSp;
    String[] data = {"русский", "английский", "казахский", "украинский", "немецкий"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.translator_fragment_layout, container, false);
        mFirstLanguageSp = (Spinner)v.findViewById(R.id.f_language);
        mSecondLanguageSp = (Spinner)v.findViewById(R.id.s_language);
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFirstLanguageSp.setAdapter(adapter);
        mSecondLanguageSp.setAdapter(adapter);
        return v;
    }
}
