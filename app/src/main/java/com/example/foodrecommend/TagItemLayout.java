package com.example.foodrecommend;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodrecommend.Entities.Food;

public class TagItemLayout extends LinearLayout {

    public TagItemLayout(final Context context, final Food food) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.tag_recommend_item, this);
        TextView desText = findViewById(R.id.tag_recommend_describe_text);
        TextView nameText = findViewById(R.id.tag_recommend_name);
        ImageView imageView = findViewById(R.id.tag_recommend_image);
        nameText.setText(food.fName);
        Glide.with(context).load(food.picUrl).into(imageView);
        desText.setText("下面是根据您的喜好“" + food.fType + "”为您推荐的项目");
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.putExtra("fid", food.fid);
                intent.putExtra("fName", food.fName);
                intent.putExtra("fUrl", food.picUrl);
                context.startActivity(intent);
            }
        });
    }


}
