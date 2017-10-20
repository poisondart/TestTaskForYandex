package com.example.nix.testtaskforyandex;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Nix on 16.10.2017.
 */

public class TextInputActivityHost extends AbstractFragmentActivity {
    private static final String EXTRA_CL_TEXT = "com.example.nix.testtaskforyandex.text";
    private static final String EXTRA_CL_INPUT_LANG = "com.example.nix.testtaskforyandex.input_lang";

    public static Intent newIntent(Context context, String text, String input_lang){
        Intent intent = new Intent(context, TextInputActivityHost.class);
        intent.putExtra(EXTRA_CL_TEXT, text);
        intent.putExtra(EXTRA_CL_INPUT_LANG, input_lang);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String text = (String)getIntent().getSerializableExtra(EXTRA_CL_TEXT);
        String input_lang = (String)getIntent().getSerializableExtra(EXTRA_CL_INPUT_LANG);
        return TextInputFragment.newInstance(text, input_lang);
    }

    public static String getExtraText(Intent result){
        return result.getStringExtra(TextInputFragment.EXTRA_TEXT);
    }
}
