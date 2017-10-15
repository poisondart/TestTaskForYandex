package com.example.nix.testtaskforyandex;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nix.testtaskforyandex.model.Language;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nix on 12.10.2017.
 */

public class ChooseLanguageFragment extends Fragment {
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private LangAdapter mLangAdapter;
    private LangsDatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDb;
    private Cursor mUserCursor;
    private byte mType;
    private static final String ARG_TYPE = "lang_type";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = (byte)getArguments().getSerializable(ARG_TYPE);
        setRetainInstance(true);
    }
    public static ChooseLanguageFragment newInstance(byte type){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TYPE, type);
        ChooseLanguageFragment fragment = new ChooseLanguageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.choose_lang_fragment, container, false);
        mToolbar = (Toolbar)v.findViewById(R.id.choose_lg_toolbar);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.choose_lg_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mToolbar.setTitle(R.string.choose_lang_title);
        mToolbar.setNavigationIcon(R.drawable.ic_back_button);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mDatabaseHelper = new LangsDatabaseHelper(getContext());
        mDatabaseHelper.createDb();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        try{
            mDb =  mDatabaseHelper.open();
            List<Language> languages = new ArrayList<>();
            String[] headers = new String[] {LangsDatabaseHelper.COLUMN_NAME, LangsDatabaseHelper.COLUMN_CODE};
            mUserCursor = mDb.query(LangsDatabaseHelper.TABLE, headers, null, null, null, null, null);
            int iname  = mUserCursor.getColumnIndex(LangsDatabaseHelper.COLUMN_NAME);
            int icode = mUserCursor.getColumnIndex(LangsDatabaseHelper.COLUMN_CODE);

            for(mUserCursor.moveToFirst(); !mUserCursor.isAfterLast(); mUserCursor.moveToNext()){
                String name = mUserCursor.getString(iname);
                String code = mUserCursor.getString(icode);
                languages.add(new Language(name, code));
            }
            mLangAdapter = new LangAdapter(languages, getContext(), mType);
            mRecyclerView.setAdapter(mLangAdapter);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDb.close();
        mUserCursor.close();
    }

}
