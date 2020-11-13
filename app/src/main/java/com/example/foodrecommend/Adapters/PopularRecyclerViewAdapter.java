package com.example.foodrecommend.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodrecommend.Entities.Food;
import com.example.foodrecommend.FoodDetailActivity;
import com.example.foodrecommend.R;

import java.util.List;

public class PopularRecyclerViewAdapter extends RecyclerView.Adapter<PopularRecyclerViewAdapter.ViewHolder> {
    List<Food> foods;
Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            imageView = itemView.findViewById(R.id.popular_recommend_image);
            textView = itemView.findViewById(R.id.popular_recommend_text);
        }
    }

    public PopularRecyclerViewAdapter(List<Food> foods) {
        this.foods = foods;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.popular_recommend_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.putExtra("fid",foods.get(holder.getAdapterPosition()).fid);
                intent.putExtra("fName",foods.get(holder.getAdapterPosition()).fName);
                intent.putExtra("fUrl",foods.get(holder.getAdapterPosition()).picUrl);

                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Food food = foods.get(i);
        viewHolder.textView.setText(food.fName);
        Glide.with(context).load(food.picUrl).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }
}
