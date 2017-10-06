package com.example.nix.testtaskforyandex.tabs_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nix.testtaskforyandex.R;

/**
 * Created by Nix on 07.10.2017.
 */

public class FavoritesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.favorites_tab_layout, container, false);
        return v;
    }
}
