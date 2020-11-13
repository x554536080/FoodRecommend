package com.example.foodrecommend.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodrecommend.R;
import com.example.foodrecommend.SocialNetworkActivity;
import com.example.foodrecommend.UserDetailActivity;

import java.util.List;

public class SocialNetworkRecyclerViewAdapter extends RecyclerView.Adapter<SocialNetworkRecyclerViewAdapter.ViewHolder> {

    List<String> users;
    Context context;
    String function;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textView;
        Button button;
        Context context;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.social_item_layout);
            textView = itemView.findViewById(R.id.social_item_name);
            button = itemView.findViewById(R.id.social_item_button);
        }

    }

    public SocialNetworkRecyclerViewAdapter(List<String> users, String function) {
        this.function = function;
        this.users = users;
    }


    @NonNull
    @Override
    public SocialNetworkRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (context == null) {
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.social_network_item, viewGroup, false);
        final SocialNetworkRecyclerViewAdapter.ViewHolder holder = new ViewHolder(view);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserDetailActivity.class);
                intent.putExtra("username", users.get(holder.getAdapterPosition()));

                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SocialNetworkRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        String user = users.get(i);
        viewHolder.textView.setText(user);
        if (function.equals("followers")) {
            viewHolder.button.setText("关注");
            if (UserDetailActivity.followings.contains(user)) viewHolder.button.setText("相互关注");
        } else {
            if (UserDetailActivity.followers.contains(user)) viewHolder.button.setText("相互关注");
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
