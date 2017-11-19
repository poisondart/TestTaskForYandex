package com.example.nix.testtaskforyandex;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.nix.testtaskforyandex.model.LocalBDItem;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nix on 19.11.2017.
 */

public abstract class AbstractTabFragment extends Fragment {
    private TextView mInfoView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private Realm mRealm;
    private RealmResults<LocalBDItem> mItems;
    private LocalBDItemAdapter mAdapter;

    protected abstract String setInfoView();
    protected abstract RealmResults<LocalBDItem> getItemsFromRealms(Realm realm);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getContext());
        mRealm = Realm.getDefaultInstance();
        mItems = getItemsFromRealms(mRealm);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.abstract_tab_layout, container, false);
        mInfoView = (TextView) v.findViewById(R.id.placeholder_abstract);
        mInfoView.setText(setInfoView());
        if(mItems.isEmpty()) mInfoView.setVisibility(View.VISIBLE);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.abstract_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new LocalBDItemAdapter(mItems, mRealm);
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }
}
