package com.example.foodrecommend;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodrecommend.Entities.Food;
import com.example.foodrecommend.HttpThreads.FetchingFoodDetailThread;
import com.example.foodrecommend.HttpThreads.FetchingFoodTypesThread;
import com.example.foodrecommend.HttpThreads.UploadingRatingThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodDetailActivity extends AppCompatActivity {

    public static String fid;
    public static String fName;
    public static String fUrl;
    public static String fType;
    public static JSONObject foodInfo;

    TextView describeText;
    TextView scoreNumberText;
    TextView scoreText;
    LinearLayout tagLayout;
    FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        fid = getIntent().getStringExtra("fid");
        new FetchingFoodDetailThread(handler, fid).start();
        fName = getIntent().getStringExtra("fName");
        fUrl = getIntent().getStringExtra("fUrl");
        Toolbar toolbar = findViewById(R.id.food_detail_toolBar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.food_detail_CTLayout);
        ImageView imageView = findViewById(R.id.food_detail_image);
        describeText = findViewById(R.id.food_detail_describe_text);
        scoreText = findViewById(R.id.food_detail_score_text);
        tagLayout = findViewById(R.id.food_detail_tag_layout);
        scoreNumberText = findViewById(R.id.food_detail_rater_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitle(fName);
        Glide.with(this).load(fUrl).into(imageView);
        button = findViewById(R.id.food_detail_rating_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.hasLogged){
                    PopupMenu popupMenu = new PopupMenu(FoodDetailActivity.this, view);
                    popupMenu.getMenuInflater().inflate(R.menu.rating_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int score = 0;
                            switch (item.getItemId()) {
                                case R.id.rating_menu_1:
                                    score = 1;
                                    break;
                                case R.id.rating_menu_2:
                                    score = 2;
                                    break;
                                case R.id.rating_menu_3:
                                    score = 3;
                                    break;
                                case R.id.rating_menu_4:
                                    score = 4;
                                    break;
                                case R.id.rating_menu_5:
                                    score = 5;
                                    break;
                            }
                            /*暂时只加到type评分里*/
                            new UploadingRatingThread(fid, fType, score + "", handler).start();
                            return true;
                        }
                    });
                    popupMenu.show();

                }else {
                    Toast.makeText(FoodDetailActivity.this,"请先登陆后再评分",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        describeText.setText(foodInfo.getString("des"));
                        if (!foodInfo.getString("score").equals("no score")) {
                            scoreText.setText(foodInfo.getString("score"));
                        } else scoreText.setText("N/A");
                        if (!foodInfo.getString("scoreNum").equals("0")) {
                            scoreNumberText.setText(foodInfo.getString("scoreNum"));
                        } else scoreNumberText.setText("暂时还没有人评分哦");
                        JSONArray tags = foodInfo.getJSONArray("tags");
                        for (int i = 0; i < tags.length(); i++) {
                            TextView textView = new TextView(FoodDetailActivity.this);
                            textView.setText(tags.getString(i));
                            if (i == 0) {
                                fType = tags.getString(i);
                            }
                            textView.setGravity(Gravity.BOTTOM);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            layoutParams.setMargins(12, 15, 12, 0);
                            tagLayout.addView(textView, layoutParams);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    Toast.makeText(FoodDetailActivity.this, "评分成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
