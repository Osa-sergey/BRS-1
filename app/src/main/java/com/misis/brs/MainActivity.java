package com.misis.brs;

import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.app.Fragment;

import android.view.Menu;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.misis.brs.Fragments.ContactsFragment;
import com.misis.brs.Fragments.HomeFragment;
import com.misis.brs.Fragments.HometasksFragment;
import com.misis.brs.Fragments.MarksFragment;
import com.misis.brs.Fragments.NewsFragment;
import com.misis.brs.Fragments.NewsViewFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ContactsFragment contactsFragment;
    HomeFragment homeFragment;
    HometasksFragment hometasksFragment;
    MarksFragment marksFragment;
    NewsFragment newsFragment;
    NewsViewFragment newsViewFragment;

    ImageButton ibDropdown;

    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    Boolean isClosed;

    LinearLayout extraInfo;
    LinearLayout llFirstLabel, llSecondLabel, llThirdLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isClosed=true;

        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.nav_view_bottom);

        ibDropdown = navigationView.getHeaderView(0).findViewById(R.id.extraInfoButton);
        extraInfo = navigationView.getHeaderView(0).findViewById(R.id.extra);
        extraInfo.setVisibility(View.GONE);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        contactsFragment = new ContactsFragment();
        homeFragment = new HomeFragment();
        hometasksFragment = new HometasksFragment();
        marksFragment = new MarksFragment();
        newsFragment = new NewsFragment();
        newsViewFragment = new NewsViewFragment();

        llFirstLabel = findViewById(R.id.llMenuFirstLabel);
        llSecondLabel = findViewById(R.id.llMenuSecondLabel);
        llThirdLabel = findViewById(R.id.llMenuThirdLabel);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        replaceFragment(R.id.themaincontainer,homeFragment);
                        bottomMenuStick(1);
                        return true;
                    case R.id.navigation_points:
                        replaceFragment(R.id.themaincontainer,marksFragment);
                        bottomMenuStick(2);
                        return true;
                    case R.id.navigation_hometask:
                        replaceFragment(R.id.themaincontainer,hometasksFragment);
                        bottomMenuStick(3);
                        return true;
                }
                return false;
            }
        });

        ibDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClosed)
                    extraInfo.setVisibility(View.VISIBLE);
                else
                    extraInfo.setVisibility(View.GONE);
                isClosed = !isClosed;
            }
        });

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.themaincontainer,homeFragment);
        ft.commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        try {
            switch (id) {
                case R.id.News:
                    replaceFragment(R.id.themaincontainer,newsFragment);
                    break;
                case R.id.Contacts:
                    replaceFragment(R.id.themaincontainer,contactsFragment);
                    break;
                case R.id.BugReport:
                    // TODO: Добавить баг репорт
                    break;
                case R.id.Help:
                    //
                    break;
                case R.id.Settings:
                    //
                    break;
            }

            llFirstLabel.setVisibility(View.GONE);
            llSecondLabel.setVisibility(View.GONE);
            llThirdLabel.setVisibility(View.GONE);

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    public void replaceFragment(int res, Fragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(res,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void bottomMenuStick(int configuration){
        switch (configuration){
            case 1:
                llFirstLabel.setVisibility(View.VISIBLE);
                llSecondLabel.setVisibility(View.INVISIBLE);
                llThirdLabel.setVisibility(View.INVISIBLE);
                break;
            case 2:
                llFirstLabel.setVisibility(View.INVISIBLE);
                llSecondLabel.setVisibility(View.VISIBLE);
                llThirdLabel.setVisibility(View.INVISIBLE);
                break;
            case 3:
                llFirstLabel.setVisibility(View.INVISIBLE);
                llSecondLabel.setVisibility(View.INVISIBLE);
                llThirdLabel.setVisibility(View.VISIBLE);
                break;
        }
    }
}
