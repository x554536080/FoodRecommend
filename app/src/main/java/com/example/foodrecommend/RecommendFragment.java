package com.example.foodrecommend;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodrecommend.Adapters.PopularRecyclerViewAdapter;
import com.example.foodrecommend.Entities.Food;
import com.example.foodrecommend.HttpThreads.GettingGuessingFoodsThread;
import com.example.foodrecommend.HttpThreads.GettingPopularFoodsThread;
import com.example.foodrecommend.HttpThreads.GettingTagFoodsThread;

import java.util.ArrayList;
import java.util.List;

import static com.example.foodrecommend.MainActivity.hasLogged;
import static com.example.foodrecommend.MainActivity.username;

public class RecommendFragment extends Fragment {
    public static PopularRecyclerViewAdapter popularRecyclerViewAdapter;
    public static PopularRecyclerViewAdapter guessingRecyclerViewAdapter;

    RecyclerView popularRecyclerView;
    RecyclerView guessingRecyclerView;
    LinearLayout tagLayout;

    FrameLayout frameLayout;
    FrameLayout frameLayout1;
    ProgressBar popularProgressBar;
    ProgressBar guessingProgressBar;
    TextView notLoginText;
    public static List<Food> popularFoodList;
    public static List<Food> guessingFoodList;
    public static List<Food> tagFoodList;

    long startTime;
    long endTime;


    boolean hasRecommended;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        hasRecommended = false;

        popularFoodList = new ArrayList<>();
        guessingFoodList = new ArrayList<>();
        tagFoodList = new ArrayList<>();

        new GettingPopularFoodsThread(handler).start();


        View view = inflater.inflate(R.layout.recommend_fragment, container, false);

        frameLayout = view.findViewById(R.id.recommend_frame_layout);
        frameLayout1 = view.findViewById(R.id.recommend_frame_layout1);

        popularProgressBar = view.findViewById(R.id.popular_progressBar);
        guessingProgressBar = view.findViewById(R.id.guessing_progressBar);
        notLoginText = view.findViewById(R.id.guessing_not_login_text);
        notLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        if (!hasLogged) {
            guessingProgressBar.setVisibility(View.GONE);
            notLoginText.setVisibility(View.VISIBLE);
        }

        popularRecyclerView = view.findViewById(R.id.popular_recommend_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        popularRecyclerView.setLayoutManager(layoutManager);
        popularRecyclerViewAdapter = new PopularRecyclerViewAdapter(popularFoodList);
        popularRecyclerView.setAdapter(popularRecyclerViewAdapter);

        guessingRecyclerView = view.findViewById(R.id.guessing_recommend_recycler_view);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        guessingRecyclerView.setLayoutManager(layoutManager2);
        guessingRecyclerViewAdapter = new PopularRecyclerViewAdapter(guessingFoodList);
        guessingRecyclerView.setAdapter(guessingRecyclerViewAdapter);

        tagLayout = view.findViewById(R.id.recommend_linear_layout);
        startTime = System.currentTimeMillis();
        return view;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    popularRecyclerViewAdapter.notifyDataSetChanged();
                    popularProgressBar.setVisibility(View.GONE);
                    break;
                case 2:
                    frameLayout1.setVisibility(View.VISIBLE);
                    guessingRecyclerViewAdapter.notifyDataSetChanged();
                    guessingProgressBar.setVisibility(View.GONE);
                    break;
                case 3:
                    /*加载标签推荐*/
                    for (Food food : tagFoodList) {
                        tagLayout.addView(new TagItemLayout(getActivity(), food));
                    }
                    guessingProgressBar.setVisibility(View.GONE);
                    endTime = System.currentTimeMillis();
                    Log.d("推荐结果加载时间",endTime-startTime+"");
                    break;
                case 4:
                    /*没有协同过滤*/
                    frameLayout1.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (hasLogged) {
            if (!hasRecommended) {
                guessingProgressBar.setVisibility(View.VISIBLE);
                new GettingGuessingFoodsThread(handler, username).start();
                new GettingTagFoodsThread(handler, username).start();
                hasRecommended = true;
            }
            notLoginText.setVisibility(View.GONE);


        }
    }
}
