package com.example.nix.testtaskforyandex.tabs_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.nix.testtaskforyandex.LocalBDItemAdapter;
import com.example.nix.testtaskforyandex.R;
import com.example.nix.testtaskforyandex.model.LocalBDItem;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nix on 07.10.2017.
 */

public class FavoritesFragment extends Fragment {
    private TextView mInfoView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private Realm mRealm;
    private RealmResults<LocalBDItem> mFavItems;
    private LocalBDItemAdapter mFavAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getContext());
        mRealm = Realm.getDefaultInstance();
        mFavItems = getItemsFromRealms(mRealm);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favorites_tab_layout, container, false);
        mInfoView = (TextView) v.findViewById(R.id.placeholder_fav);
        if(mFavItems.isEmpty()) mInfoView.setVisibility(View.VISIBLE);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.fav_tab_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFavAdapter = new LocalBDItemAdapter(mFavItems, mRealm);
        mRecyclerView.setAdapter(mFavAdapter);
        return v;
    }

    private RealmResults<LocalBDItem> getItemsFromRealms(Realm realm){
        RealmResults<LocalBDItem> results = realm.where(LocalBDItem.class)
                .equalTo(LocalBDItem.IS_FAVORITES, true).findAll();
        return results;
    }
}
