package com.saumya.fitmate;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    private TextView mTextMessage;
    private FragmentManager manager;
    private FragmentTransaction mFragmenttrans;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Home mHome = new Home();
                    manager = getSupportFragmentManager();
                    mFragmenttrans = manager.beginTransaction();
                    mFragmenttrans.replace(R.id.rootLayout, mHome);


                    mFragmenttrans.commit();
                    return true;
                case R.id.navigation_activities:
                    Discover mDisc = new Discover();
                    manager = getSupportFragmentManager();
                    mFragmenttrans = manager.beginTransaction();
                    mFragmenttrans.replace(R.id.rootLayout, mDisc);

                    mFragmenttrans.commit();
                    return true;
                case R.id.navigation_discover:
                    Activities mAc = new Activities();
                    manager = getSupportFragmentManager();
                    mFragmenttrans = manager.beginTransaction();
                    mFragmenttrans.replace(R.id.rootLayout, mAc);

                    mFragmenttrans.commit();
                    return true;
                case R.id.navigation_fav:
                    Favourites mFav = new Favourites();
                    manager = getSupportFragmentManager();
                    mFragmenttrans = manager.beginTransaction();
                    mFragmenttrans.replace(R.id.rootLayout, mFav);

                    mFragmenttrans.commit();
                    return true;
                case R.id.navigation_profile:
                    Profile mProf = new Profile();
                    manager = getSupportFragmentManager();
                    mFragmenttrans = manager.beginTransaction();
                    mFragmenttrans.replace(R.id.rootLayout, mProf);

                    mFragmenttrans.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationHelper.disableShiftMode(navigation);
        Home mHome = new Home();
        manager = getSupportFragmentManager();
        mFragmenttrans = manager.beginTransaction();
        mFragmenttrans.replace(R.id.rootLayout, mHome);
        mFragmenttrans.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    protected void onStop() {
        manager = getSupportFragmentManager();
        Home mHome = new Home();
        Log.e("hello","hi");
        mFragmenttrans = manager.beginTransaction();
        finishAffinity();
        super.onStop();
    }
}
