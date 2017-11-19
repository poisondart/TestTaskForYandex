package com.example.nix.testtaskforyandex.tabs_fragments;

import com.example.nix.testtaskforyandex.AbstractTabFragment;
import com.example.nix.testtaskforyandex.R;
import com.example.nix.testtaskforyandex.model.LocalBDItem;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Nix on 07.10.2017.
 */

public class FavoritesFragment extends AbstractTabFragment {

    protected RealmResults<LocalBDItem> getItemsFromRealms(Realm realm){
        return realm.where(LocalBDItem.class)
                .equalTo(LocalBDItem.IS_FAVORITES, true).findAll();
    }

    @Override
    protected String setInfoView() {
        return getString(R.string.placeholder_fav_tab);
    }
}
