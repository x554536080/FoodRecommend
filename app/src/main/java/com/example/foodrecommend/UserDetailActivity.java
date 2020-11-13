package com.example.foodrecommend;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodrecommend.Adapters.MyFragmentPagerAdapter;
import com.example.foodrecommend.Adapters.UserDetailFragmentPagerAdapter;
import com.example.foodrecommend.HttpThreads.GettingSocialNetworkInfoThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivity extends AppCompatActivity {
    public static List<String> followings;
    public static List<String> followers;

    TextView usernameText;
    CircleImageView headImage;
    UserDetailFragmentPagerAdapter fragmentPagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView following;
    TextView follower;
    TextView followingNumber;
    TextView followerNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        followers = new ArrayList<>();
        followings = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        initViews();
        new GettingSocialNetworkInfoThread(handler, "followers").start();
        new GettingSocialNetworkInfoThread(handler, "followings").start();
    }

    void initViews() {
        followerNumber = findViewById(R.id.user_detail_follower_number);
        followingNumber = findViewById(R.id.user_detail_following_number);
        usernameText = findViewById(R.id.user_detail_username);
        usernameText.setText(MainActivity.username);
        follower = findViewById(R.id.user_detail_follower);
        follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailActivity.this, SocialNetworkActivity.class);
                intent.putExtra("function", "followers");
                startActivity(intent);
            }
        });
        following = findViewById(R.id.user_detail_following);
        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailActivity.this, SocialNetworkActivity.class);
                intent.putExtra("function", "followings");
                startActivity(intent);
            }
        });
        viewPager = findViewById(R.id.user_detail_view_pager);
        tabLayout = findViewById(R.id.user_detail_tab_layout);
        fragmentPagerAdapter = new UserDetailFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            followerNumber.setText(followers.size()+"");
            followingNumber.setText(followings.size()+"");
        }
    };
}
