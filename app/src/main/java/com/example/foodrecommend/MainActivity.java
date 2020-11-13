package com.example.foodrecommend;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.foodrecommend.Adapters.MyFragmentPagerAdapter;
import com.example.foodrecommend.HttpThreads.FetchingFoodTypesThread;
import com.example.foodrecommend.HttpThreads.GettingPopularFoodsThread;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //logic
    public static boolean hasLogged;
    public static String username;



    //views
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView usernameText;
    CircleImageView headImage;
    NavigationMenuItemView loginItem;
    ViewPager viewPager;
    MyFragmentPagerAdapter fragmentPagerAdapter;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initLogicVariables();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgetVariables();

    }

    void initLogicVariables() {
        new FetchingFoodTypesThread().start();
        hasLogged = false;
    }


    void initWidgetVariables() {
        //设置分页碎片
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        navigationView = findViewById(R.id.nav_view);
        navigationView.inflateHeaderView(R.layout.nav_header);
        loginItem = findViewById(R.id.nav_login);
        View headerView = navigationView.getHeaderView(0);
//        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_login:
                        if (!hasLogged)
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        else {
                            startActivity(new Intent(MainActivity.this, UserDetailActivity.class));
                        }
                    default:
                        break;
                }
                return true;
            }
        });
        usernameText = headerView.findViewById(R.id.nav_user_nickname);
        headImage = headerView.findViewById(R.id.nav_user_pic);
        headImage.setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.nav_user_pic:
                if (!hasLogged)
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                else
                    startActivity(new Intent(MainActivity.this, UserDetailActivity.class));

                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasLogged) {
            usernameText.setText(username);
        }
    }
}
