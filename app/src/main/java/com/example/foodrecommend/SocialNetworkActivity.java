package com.example.foodrecommend;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.foodrecommend.Adapters.SocialNetworkRecyclerViewAdapter;

import static com.example.foodrecommend.UserDetailActivity.followers;
import static com.example.foodrecommend.UserDetailActivity.followings;

public class SocialNetworkActivity extends AppCompatActivity {




    SocialNetworkRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_network);
        Toolbar toolbar = findViewById(R.id.social_toolbar);
        setSupportActionBar(toolbar);
        String function = getIntent().getStringExtra("function");
        if (getSupportActionBar() != null) {
            if (function.equals("followings"))
                getSupportActionBar().setTitle("我的关注");
            else getSupportActionBar().setTitle("我的粉丝");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.social_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if (function.equals("followings")) {
            adapter = new SocialNetworkRecyclerViewAdapter(followings, "followings");
            recyclerView.setAdapter(adapter);

        } else adapter = new SocialNetworkRecyclerViewAdapter(followers, "followers");
        recyclerView.setAdapter(adapter);
    }


}
