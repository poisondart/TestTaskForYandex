package com.example.nix.testtaskforyandex;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CLHostActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clhost);
        mFragmentManager = getSupportFragmentManager();
        mFragment = mFragmentManager.findFragmentById(R.id.fragment_container);
        mFragment = new ChooseLanguageFragment();
        mFragmentManager.beginTransaction()
                .add(R.id.choose_lg_host, mFragment)
                .commit();
    }
}
