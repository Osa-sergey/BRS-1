package com.misis.brs;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.misis.brs.Database.DBHelper;
import com.misis.brs.Fragments.ContactsFragment;
import com.misis.brs.Fragments.HomeFragment;
import com.misis.brs.Fragments.NewsFragment;
import com.misis.brs.Fragments.MarksFragment;
import com.misis.brs.Fragments.HometasksFragment;


public class MainActivity extends AppCompatActivity {

    private HomeFragment hF;
    private MarksFragment mF;
    private HometasksFragment tF;
    private NewsFragment nF;
    private ContactsFragment cF;

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageButton button;
    private LinearLayout linearLayout;
//    private ActionBarDrawerToggle toggle;
    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelector =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.homeFragment:
                            setFragment(hF);
                            return true;
                        case R.id.pointsFragment:
                            setFragment(mF);
                            return true;
                        case R.id.tasksFragment:
                            setFragment(tF);
                            return true;
                    }
                    return false;
                }
            };
    private NavigationView.OnNavigationItemSelectedListener sideNavSelector =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.News:
                            setFragment(nF);
                        //    drawer.closeDrawer(GravityCompat.START);
                            break;
                        case R.id.Contacts:
                            setFragment(cF);
                         //   drawer.closeDrawer(GravityCompat.START);
                            return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linearLayout = (LinearLayout) findViewById(R.id.extra);
        button = (ImageButton) findViewById(R.id.extraInfoButton);
      //не найдены linearLayout button
        /*  button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayout.getVisibility() == View.VISIBLE)
                    linearLayout.setVisibility(View.GONE);
                else{
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
       */ DBHelper.getInstance(getApplicationContext(),"test");
        //TODO выяснить почему не видится drawer_layout
        View view = findViewById(R.id.include1);
        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//null pointer exception
   /*     toggle = new ActionBarDrawerToggle(,
                drawer,toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
*/
        nF = new NewsFragment();
        cF = new ContactsFragment();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(sideNavSelector);

        hF = new HomeFragment();
        mF = new MarksFragment();
        tF = new HometasksFragment();

        setFragment(hF);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_menu);
        navigation.setOnNavigationItemSelectedListener(navItemSelector);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.replace, fragment);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }
}
