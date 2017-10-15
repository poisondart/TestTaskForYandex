package com.example.nix.testtaskforyandex;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.nix.testtaskforyandex.bottom_nav_fragments.HistoryAndFavFragment;
import com.example.nix.testtaskforyandex.bottom_nav_fragments.SettingsFragment;
import com.example.nix.testtaskforyandex.bottom_nav_fragments.TranslationFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationViewEx mBottomNavigationViewEx;
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigationViewEx = (BottomNavigationViewEx)findViewById(R.id.bottom_navigation);
        mFragmentManager = getSupportFragmentManager();
        mFragment = mFragmentManager.findFragmentById(R.id.fragment_container);
        mFragment = new TranslationFragment();
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, mFragment)
                .commit();
        mBottomNavigationViewEx.setIconSize(26.0f, 26.0f);
        mBottomNavigationViewEx.setTextVisibility(false);
        mBottomNavigationViewEx.enableAnimation(true);
        mBottomNavigationViewEx.enableShiftingMode(false);
        mBottomNavigationViewEx.enableItemShiftingMode(false);
        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_translator:
                        setFragment(new TranslationFragment());
                        break;
                    case R.id.action_history_and_favorites:
                        setFragment(new HistoryAndFavFragment());
                        break;
                    case R.id.action_settings:
                        setFragment(new SettingsFragment());
                        break;
                }
                return true;
            }
        });
    }
    private void setFragment(Fragment fragment){
        mFragmentManager.beginTransaction()
                .remove(mFragment)
                .commit();
        mFragment = fragment;
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, mFragment)
                .commit();
    }
}
