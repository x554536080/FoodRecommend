package com.example.foodrecommend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    //data
    int currentPage;
    int totalPageNumber;
    List<Map<String, Object>> dataList;
    static public ArrayList<String> foodTypes = new ArrayList<>();

    //views
    GridView gridView;
    SimpleAdapter simpleAdapter;
    Button buttonPrevious;
    Button buttonNext;
    TextView pageNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.type_fragment, container, false);
        gridView = view.findViewById(R.id.type_fragment_grid_view);
        buttonNext = view.findViewById(R.id.type_button_next);
        buttonPrevious = view.findViewById(R.id.type_button_previous);
        buttonPrevious.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        pageNumber = view.findViewById(R.id.food_type_page_number);

        if(!foodTypes.isEmpty()){
        initGrid();
        String from[] = {"img", "text"};
        int to[] = {R.id.type_item_img, R.id.type_item_text};
        simpleAdapter = new SimpleAdapter(this.getActivity(), dataList, R.layout.gridview_item, from, to);
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(this);}

        return view;

    }

    void initGrid() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("img", R.drawable.dish);
            map.put("text", foodTypes.get(i));
            dataList.add(map);
        }
        if (foodTypes.size() % 20 == 0) {
            totalPageNumber = foodTypes.size() / 20;
        } else totalPageNumber = foodTypes.size() / 20 + 1;
        currentPage = 1;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this.getActivity(), SpecificTypeFoodActivity.class);
        intent.putExtra("type", (String) dataList.get(i).get("text"));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.type_button_next:
                if (currentPage < totalPageNumber) {
                    currentPage++;
                    pageNumber.setText("第" + currentPage + "页");
                    dataList.clear();
                    for (int i = 20 * (currentPage-1); i < 20 * (currentPage-1) + 20 && i < foodTypes.size(); i++) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("img", R.drawable.dish);
                        map.put("text", foodTypes.get(i));
                        dataList.add(map);
                    }
                    simpleAdapter.notifyDataSetChanged();
                } else Toast.makeText(this.getActivity(), "已经是最后一页啦", Toast.LENGTH_SHORT).show();

                break;
            case R.id.type_button_previous:
                if (currentPage > 1) {
                    currentPage--;
                    pageNumber.setText("第" + currentPage + "页");
                    dataList.clear();
                    for (int i = 20 * (currentPage-1); i < 20 * (currentPage-1) + 20 && i < foodTypes.size(); i++) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("img", R.drawable.dish);
                        map.put("text", foodTypes.get(i));
                        dataList.add(map);
                    }
                    simpleAdapter.notifyDataSetChanged();
                } else Toast.makeText(this.getActivity(), "已经是第一页啦", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
