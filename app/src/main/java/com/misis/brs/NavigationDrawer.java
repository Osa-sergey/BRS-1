package com.misis.brs;

import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iammert.library.readablebottombar.ReadableBottomBar;
import com.misis.brs.Fragments.ContactsFragment;
import com.misis.brs.Fragments.HomeFragment;
import com.misis.brs.Fragments.HometasksFragment;
import com.misis.brs.Fragments.MarksFragment;
import com.misis.brs.Fragments.NewsFragment;
import com.misis.brs.Fragments.NewsViewFragment;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ContactsFragment contactsFragment;
    HomeFragment homeFragment;
    HometasksFragment hometasksFragment;
    MarksFragment marksFragment;
    NewsFragment newsFragment;
    NewsViewFragment newsViewFragment;

    FragmentTransaction fTrans;

    ImageButton ibDropdown;

    NavigationView navigationView;
    LinearLayout extraInfo;

    Boolean isClosed;
    ReadableBottomBar readableBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isClosed=true;

        navigationView = findViewById(R.id.nav_view);
        readableBottomBar = findViewById(R.id.readablebottom);

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

        contactsFragment = new ContactsFragment();
        homeFragment = new HomeFragment();
        hometasksFragment = new HometasksFragment();
        marksFragment = new MarksFragment();
        newsFragment = new NewsFragment();
        newsViewFragment = new NewsViewFragment();

        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.themaincontainer, homeFragment);
        fTrans.commit();
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

        fTrans = getFragmentManager().beginTransaction();

        try {
            switch (id) {
                case R.id.News:
                    fTrans.replace(R.id.themaincontainer, newsFragment);


                    break;
                case R.id.Contacts:
                    fTrans.replace(R.id.themaincontainer, contactsFragment);


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

            fTrans.commit();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
