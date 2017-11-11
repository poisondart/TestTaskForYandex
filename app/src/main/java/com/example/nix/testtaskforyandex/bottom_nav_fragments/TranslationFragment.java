package com.example.nix.testtaskforyandex.bottom_nav_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nix.testtaskforyandex.CLHostActivity;
import com.example.nix.testtaskforyandex.Constants;
import com.example.nix.testtaskforyandex.Preferences;
import com.example.nix.testtaskforyandex.R;
import com.example.nix.testtaskforyandex.ResultsAdapter;
import com.example.nix.testtaskforyandex.TextInputActivityHost;
import com.example.nix.testtaskforyandex.YandexTranslateAPI;
import com.example.nix.testtaskforyandex.model.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nix on 06.10.2017.
 */

public class TranslationFragment extends Fragment implements View.OnClickListener{
    private Button mFirstLanguageButton, mSecondLanguageButton;
    private ImageButton mSwitchLanguageButton, mDeleteTextButton;
    private TextView mTextView;
    private String mString = "";
    private RecyclerView mRecyclerView;
    private ResultsAdapter mAdapter;
    private ArrayList<String> mResultsExample;
    private Result mResult;
    private Gson mGson;
    private Retrofit mRetrofit;
    private YandexTranslateAPI mAPI;
    public static final byte REQUEST_CODE = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.translator_fragment_layout, container, false);
        Preferences.initialize(getContext());
        mResultsExample = new ArrayList<>();
        mFirstLanguageButton = (Button)v.findViewById(R.id.f_lan_button);
        mSecondLanguageButton = (Button)v.findViewById(R.id.s_lan_button);
        mSwitchLanguageButton = (ImageButton)v.findViewById(R.id.switch_lang);
        mDeleteTextButton = (ImageButton) v.findViewById(R.id.delete_text_button);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerview_results);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTextView = (TextView)v.findViewById(R.id.textview_request);
        mTextView.setText(R.string.query_hint);
        mFirstLanguageButton.setOnClickListener(this);
        mSecondLanguageButton.setOnClickListener(this);
        mSwitchLanguageButton.setOnClickListener(this);
        mDeleteTextButton.setOnClickListener(this);
        mTextView.setOnClickListener(this);

        mGson = new GsonBuilder().create();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .baseUrl(Constants.URL)
                .build();
        mAPI = mRetrofit.create(YandexTranslateAPI.class);

        mAdapter = new ResultsAdapter(mResultsExample);
        mRecyclerView.setAdapter(mAdapter);

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
            setResults(mString);
        }
    }

    private void setResults(String string){
        String lang = Preferences.getFirstLanguageCode(getContext()) + "-" + Preferences.getSecondLanguageCode(getContext());
        mAPI.getResult(Constants.API_KEY, string, lang).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                mResult = response.body();
                if(mResult == null){
                    Log.d("mResult.getText()", " is null");
                }else{
                    mResultsExample.addAll(mResult.getText());
                    Log.d("Code: ", mResult.getCode().toString());
                    Log.d("Lang: ", mResult.getLang());
                    for(String st : mResult.getText()){
                        Log.d("Text: ", st);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("onFailure","An error occurred during networking");
                Toast.makeText(getContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
