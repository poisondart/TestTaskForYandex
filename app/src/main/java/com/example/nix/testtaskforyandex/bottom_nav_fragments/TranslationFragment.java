package com.example.nix.testtaskforyandex.bottom_nav_fragments;

import android.app.Activity;
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
import android.widget.TextView;
import com.example.nix.testtaskforyandex.CLHostActivity;
import com.example.nix.testtaskforyandex.Preferences;
import com.example.nix.testtaskforyandex.R;
import com.example.nix.testtaskforyandex.TextInputActivityHost;

/**
 * Created by Nix on 06.10.2017.
 */

public class TranslationFragment extends Fragment implements View.OnClickListener{
    private Button mFirstLanguageButton, mSecondLanguageButton;
    private ImageButton mSwitchLanguageButton, mDeleteTextButton;
    private TextView mTextView;
    private String mString = "";
    public static final byte REQUEST_CODE = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.translator_fragment_layout, container, false);
        Preferences.initialize(getContext());
        mFirstLanguageButton = (Button)v.findViewById(R.id.f_lan_button);
        mSecondLanguageButton = (Button)v.findViewById(R.id.s_lan_button);
        mSwitchLanguageButton = (ImageButton)v.findViewById(R.id.switch_lang);
        mDeleteTextButton = (ImageButton) v.findViewById(R.id.delete_text_button);
        mTextView = (TextView)v.findViewById(R.id.textview_request);
        mTextView.setText(R.string.query_hint);
        mFirstLanguageButton.setOnClickListener(this);
        mSecondLanguageButton.setOnClickListener(this);
        mSwitchLanguageButton.setOnClickListener(this);
        mDeleteTextButton.setOnClickListener(this);
        mTextView.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        Context context = getContext();
        switch (view.getId()){
            case R.id.f_lan_button:
                intent = CLHostActivity.newIntent(context, CLHostActivity.FIRST_LANGUAGE_CHOOSE);
                startActivity(intent);
                break;
            case R.id.s_lan_button:
                intent = CLHostActivity.newIntent(context, CLHostActivity.SECOND_LANGUAGE_CHOOSE);
                startActivity(intent);
                break;
            case R.id.switch_lang:
                Preferences.switchLangs(context);
                updateUI();
                break;
            case R.id.delete_text_button:
                mString = "";
                mTextView.setText(R.string.query_hint);
                break;
            case R.id.textview_request:
                intent = TextInputActivityHost.newIntent(context, mString, Preferences.getFirstLanguage(context));
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }
    private void updateUI(){
        mFirstLanguageButton.setText(Preferences.getFirstLanguage(getContext()));
        mSecondLanguageButton.setText(Preferences.getSecondLanguage(getContext()));
        if(mString.length() > 0){
            mTextView.setText(mString);
        }else{
         mTextView.setText(R.string.query_hint);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE) {
            if (data == null) {
                return;
            }

            mString = TextInputActivityHost.getExtraText(data);
            mTextView.setText(mString);

        }
    }
}
