package com.example.nix.testtaskforyandex.bottom_nav_fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.nix.testtaskforyandex.CLHostActivity;
import com.example.nix.testtaskforyandex.Preferences;
import com.example.nix.testtaskforyandex.R;

/**
 * Created by Nix on 06.10.2017.
 */

public class TranslationFragment extends Fragment implements View.OnClickListener{
    private Button mFirstLanguageButton, mSecondLanguageButton;
    private ImageButton mSwitchLanguageButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.translator_fragment_layout, container, false);
        Preferences.initialize(getContext());
        mFirstLanguageButton = (Button)v.findViewById(R.id.f_lan_button);
        mSecondLanguageButton = (Button)v.findViewById(R.id.s_lan_button);
        mSwitchLanguageButton = (ImageButton)v.findViewById(R.id.switch_lang);
        mFirstLanguageButton.setOnClickListener(this);
        mSecondLanguageButton.setOnClickListener(this);
        mSwitchLanguageButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.f_lan_button:
                intent = CLHostActivity.newIntent(getContext(), CLHostActivity.FIRST_LANGUAGE_CHOOSE);
                startActivity(intent);
                break;
            case R.id.s_lan_button:
                intent = CLHostActivity.newIntent(getContext(), CLHostActivity.SECOND_LANGUAGE_CHOOSE);
                startActivity(intent);
                break;
            case R.id.switch_lang:
                switchLang(getContext());
                updateUI();
                break;
        }
    }
    private void updateUI(){
        mFirstLanguageButton.setText(Preferences.getFirstLanguage(getContext()));
        mSecondLanguageButton.setText(Preferences.getSecondLanguage(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    private void switchLang(Context context){
        String misc_name = Preferences.getFirstLanguage(context);
        String misc_code = Preferences.getFirstLanguageCode(context);
        Preferences.setFirstLanguage(context, Preferences.getSecondLanguage(context));
        Preferences.setFirstLanguageCode(context, Preferences.getSecondLanguageCode(context));
        Preferences.setSecondLanguage(context, misc_name);
        Preferences.setSecondLanguageCode(context, misc_code);
    }
}
