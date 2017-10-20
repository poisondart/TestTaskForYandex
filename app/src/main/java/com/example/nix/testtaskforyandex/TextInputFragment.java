package com.example.nix.testtaskforyandex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Nix on 16.10.2017.
 */

public class TextInputFragment extends Fragment implements View.OnClickListener{
    private EditText mEditText;
    private Button mTranslateButton;
    private ImageButton mDeleteTextButton;
    private static final String ARG_TEXT = "text";
    private static final String ARG_INPUT_LANG = "lang_type";
    public static final String EXTRA_TEXT = "com.example.nix.testtaskextratext";
    private String mLangType, mText;

    public static TextInputFragment newInstance(String text, String input_lang){
        Bundle args= new Bundle();
        args.putSerializable(ARG_TEXT, text);
        args.putSerializable(ARG_INPUT_LANG, input_lang);
        TextInputFragment fragment = new TextInputFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLangType = (String)getArguments().getSerializable(ARG_INPUT_LANG);
        mText = (String)getArguments().getSerializable(ARG_TEXT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.text_input_layout, container, false);
        mEditText = (EditText) v.findViewById(R.id.text_input);
        mTranslateButton = (Button) v.findViewById(R.id.translate_button);
        mDeleteTextButton = (ImageButton) v.findViewById(R.id.delete_text_input);
        if(mText != null){
            mEditText.setText(mText);
        }
        checkText(mEditText.length());
        mEditText.setHint("Введите текст (" + mLangType + ")");
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkText(charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //nothing
            }
        });
        mDeleteTextButton.setOnClickListener(this);
        mTranslateButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete_text_input:
                mEditText.getText().clear();
                break;
            case R.id.translate_button:
                setSendText(mEditText.getText().toString());
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mEditText.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void setSendText(String text){
        Intent data = new Intent();
        data.putExtra(EXTRA_TEXT, text);
        getActivity().setResult(Activity.RESULT_OK, data);
    }
    private void checkText(int l){
        if(l > 0){
            mTranslateButton.setVisibility(View.VISIBLE);
            mDeleteTextButton.setVisibility(View.VISIBLE);
        }else {
            mTranslateButton.setVisibility(View.GONE);
            mDeleteTextButton.setVisibility(View.GONE);
        }
    }
}
