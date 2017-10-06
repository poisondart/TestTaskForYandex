package com.example.nix.testtaskforyandex.bottom_nav_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nix.testtaskforyandex.R;
import com.example.nix.testtaskforyandex.tabs_fragments.FavoritesFragment;
import com.example.nix.testtaskforyandex.tabs_fragments.HistoryFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nix on 07.10.2017.
 */

public class HistoryAndFavFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.his_and_fav_fragment_layout, container, false);
        mViewPager = (ViewPager)v.findViewById(R.id.viewpager);
        setupViewPager(mViewPager);
        mTabLayout = (TabLayout)v.findViewById(R.id.tabs);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                mTabLayout.setupWithViewPager(mViewPager);
            }
        });
        return v;
    }
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new HistoryFragment(), getString(R.string.placeholder_tabs1));
        adapter.addFragment(new FavoritesFragment(), getString(R.string.placeholder_tabs2));
        viewPager.setAdapter(adapter);
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
