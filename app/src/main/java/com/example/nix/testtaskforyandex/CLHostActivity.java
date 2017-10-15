package com.example.nix.testtaskforyandex;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CLHostActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private static final String EXTRA_CL_TYPE = "com.example.nix.testtaskforyandex.type";
    public static final byte FIRST_LANGUAGE_CHOOSE = 0;
    public static final byte SECOND_LANGUAGE_CHOOSE = 1;

    public static Intent newIntent(Context context, byte type){
        Intent intent = new Intent(context, CLHostActivity.class);
        intent.putExtra(EXTRA_CL_TYPE, type);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clhost);
        byte type = (byte)getIntent().getSerializableExtra(EXTRA_CL_TYPE);
        mFragmentManager = getSupportFragmentManager();
        mFragment = mFragmentManager.findFragmentById(R.id.fragment_container);
        mFragment = ChooseLanguageFragment.newInstance(type);
        mFragmentManager.beginTransaction()
                .add(R.id.choose_lg_host, mFragment)
                .commit();
    }
}
