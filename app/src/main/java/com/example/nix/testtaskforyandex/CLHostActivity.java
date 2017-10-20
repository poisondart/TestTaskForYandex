package com.example.nix.testtaskforyandex;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class CLHostActivity extends AbstractFragmentActivity {
    private static final String EXTRA_CL_TYPE = "com.example.nix.testtaskforyandex.type";
    public static final byte FIRST_LANGUAGE_CHOOSE = 0;
    public static final byte SECOND_LANGUAGE_CHOOSE = 1;

    public static Intent newIntent(Context context, byte type){
        Intent intent = new Intent(context, CLHostActivity.class);
        intent.putExtra(EXTRA_CL_TYPE, type);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        byte type = (byte)getIntent().getSerializableExtra(EXTRA_CL_TYPE);
        return ChooseLanguageFragment.newInstance(type);
    }
}
