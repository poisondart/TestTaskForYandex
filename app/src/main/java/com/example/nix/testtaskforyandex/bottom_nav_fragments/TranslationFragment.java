package com.example.nix.testtaskforyandex.bottom_nav_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.nix.testtaskforyandex.CLHostActivity;
import com.example.nix.testtaskforyandex.R;

/**
 * Created by Nix on 06.10.2017.
 */

public class TranslationFragment extends Fragment implements View.OnClickListener{
    private Button mFirstLanguageButton, mSecondLanguageButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.translator_fragment_layout, container, false);
        mFirstLanguageButton = (Button)v.findViewById(R.id.f_lan_button);
        mSecondLanguageButton = (Button)v.findViewById(R.id.s_lan_button);
        mFirstLanguageButton.setOnClickListener(this);
        mSecondLanguageButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.f_lan_button:
                Intent intent1 = new Intent(getActivity(), CLHostActivity.class);
                startActivity(intent1);
                break;
            case R.id.s_lan_button:
                Intent intent2 = new Intent(getActivity(), CLHostActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
