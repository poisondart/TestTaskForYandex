package com.example.nix.testtaskforyandex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Nix on 16.10.2017.
 */

public abstract class AbstractFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createFragment();
    protected Fragment mFragment;
    protected FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clhost);
        mFragmentManager = getSupportFragmentManager();
        mFragment = mFragmentManager.findFragmentById(R.id.choose_lg_host);
        if (mFragment == null) {
            mFragment = createFragment();
            mFragmentManager.beginTransaction()
                    .add(R.id.choose_lg_host, mFragment)
                    .commit();
        }
    }
}
