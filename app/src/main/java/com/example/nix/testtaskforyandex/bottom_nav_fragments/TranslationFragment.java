package com.example.nix.testtaskforyandex.bottom_nav_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nix.testtaskforyandex.CLHostActivity;
import com.example.nix.testtaskforyandex.Constants;
import com.example.nix.testtaskforyandex.LocalBDItemAdapter;
import com.example.nix.testtaskforyandex.Preferences;
import com.example.nix.testtaskforyandex.R;
import com.example.nix.testtaskforyandex.TextInputActivityHost;
import com.example.nix.testtaskforyandex.YandexTranslateAPI;
import com.example.nix.testtaskforyandex.model.LocalBDItem;
import com.example.nix.testtaskforyandex.model.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
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
    private TextView mTextView, mResultView;
    private ProgressBar mProgressBar;
    private String mString = "";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private LocalBDItemAdapter mAdapter;
    private RealmResults<LocalBDItem> mHistoryItems;
    private Result mResult;
    private Gson mGson;
    private Retrofit mRetrofit;
    private Realm mRealm;
    private YandexTranslateAPI mAPI;
    public static final byte REQUEST_CODE = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preferences.initialize(getContext());
        Realm.init(getContext());
        mRealm = Realm.getDefaultInstance();
        mHistoryItems = getItemsFromRealms(mRealm);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.translator_fragment_layout, container, false);
        mFirstLanguageButton = (Button)v.findViewById(R.id.f_lan_button);
        mSecondLanguageButton = (Button)v.findViewById(R.id.s_lan_button);
        mSwitchLanguageButton = (ImageButton)v.findViewById(R.id.switch_lang);
        mDeleteTextButton = (ImageButton) v.findViewById(R.id.delete_text_button);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progressbar);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerview_results);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mTextView = (TextView)v.findViewById(R.id.textview_request);
        mTextView.setText(R.string.query_hint);
        mResultView = (TextView) v.findViewById(R.id.textview_result);
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

        mAdapter = new LocalBDItemAdapter(mHistoryItems, mRealm);
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
                clearAll();
                break;
            /*case R.id.button_to_fav:
                if(!mString.isEmpty()){
                    RealmResults<LocalBDItem> items = mRealm.where(LocalBDItem.class)
                            .equalTo(LocalBDItem.SOURCE_PROPERTY, mString).findAll();
                    mRealm.beginTransaction();
                    for(int i = 0; i < items.size(); i++){
                        items.get(i).setFavorite(true);
                    }
                    mRealm.commitTransaction();
                }else{
                    Toast.makeText(getContext(), R.string.translate_the_word, Toast.LENGTH_LONG).show();
                }
                break;*/
            case R.id.textview_request:
                intent = TextInputActivityHost.newIntent(context, mString, Preferences.getFirstLanguage(context));
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }
    private void updateUI(){
        /*переписать - здесь лишнее выполнение кода*/
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

    private void clearAll(){
        mString = "";
        mTextView.setText(R.string.query_hint);
        mResultView.setText(R.string.result_hint);
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
        String lang_query = Preferences.getFirstLanguageCode(getContext()) + "-"
                + Preferences.getSecondLanguageCode(getContext());
        final String flang_code = Preferences.getFirstLanguageCode(getContext());
        final String slang_code = Preferences.getSecondLanguageCode(getContext());
        mProgressBar.setVisibility(View.VISIBLE);
        mAPI.getResult(Constants.API_KEY, string, lang_query).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                mResult = response.body();
                if(mResult == null){
                    Log.d("mResult.getText()", " is null");
                }else{
                    final String result = mResult.getText().get(0);
                    mResultView.setText(result);
                    writeInLog(mResult);
                    /*mRealm.beginTransaction();
                    LocalBDItem item = mRealm.createObject(LocalBDItem.class);
                    item.setSource(mString);
                    item.setResult(result);
                    item.setFCode(flang_code);
                    item.setSCode(slang_code);
                    mRealm.commitTransaction();*/


                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            LocalBDItem item = mRealm.createObject(LocalBDItem.class);
                            item.setSource(mString);
                            item.setResult(result);
                            item.setFCode(flang_code);
                            item.setSCode(slang_code);
                            mRealm.insertOrUpdate(item);
                        }
                    });


                    mAdapter.notifyDataSetChanged();
                    mLinearLayoutManager.scrollToPosition(mHistoryItems.size() - 1);
                }
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                /*заменить тост на снэкбар с предолжением повторить снова*/
                Log.d("onFailure","An error occurred during networking");
                Toast.makeText(getContext(), R.string.no_internet,
                        Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void writeInLog(Result result){
        Log.d("Code: ", result.getCode().toString());
        Log.d("Lang: ", result.getLang());
        for(String st : result.getText()){
            Log.d("Text: ", st);
        }
    }

    private RealmResults<LocalBDItem> getItemsFromRealms(Realm realm){
        RealmResults<LocalBDItem> results = realm.where(LocalBDItem.class)
                .equalTo(LocalBDItem.IS_HISTORY, true).findAll();
        return results;
    }

    @Override
    public void onPause() {
        super.onPause();
        mRealm.beginTransaction();
        RealmList<LocalBDItem> items = new RealmList<>();
        items.addAll(mHistoryItems);
        mRealm.commitTransaction();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}