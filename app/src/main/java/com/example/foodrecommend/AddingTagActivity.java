package com.example.foodrecommend;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodrecommend.HttpThreads.AddingTagThread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AddingTagActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> types;
    ArrayList<String> selectedTypes;
    HashMap<String, Integer> currentChecked;
    LinearLayout selectedLayout1;
    LinearLayout selectedLayout2;
    LinearLayout selectionLayout1;
    LinearLayout selectionLayout2;
    TextView selectedText;
    ImageButton finishButton;
    TextView skipButton;
    TextView fresh;
    Button backButton;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_tag);
        selectedLayout1 = findViewById(R.id.adding_tag_selected_layout1);
        selectedLayout2 = findViewById(R.id.adding_tag_selected_layout2);
        selectionLayout1 = findViewById(R.id.adding_tag_selections_layout1);
        selectionLayout2 = findViewById(R.id.adding_tag_selections_layout2);
        selectedText = findViewById(R.id.adding_tag_text);
        finishButton = findViewById(R.id.adding_tag_finish);
        finishButton.setOnClickListener(this);
        backButton = findViewById(R.id.adding_tag_back);
        backButton.setOnClickListener(this);
        skipButton = findViewById(R.id.adding_tag_skip);
        skipButton.setOnClickListener(this);
        fresh = findViewById(R.id.adding_tag_fresh);
        fresh.setOnClickListener(this);
        fresh.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        currentChecked = new HashMap<>();
        selectedTypes = new ArrayList<>();
        types = new ArrayList<>();
        types.addAll(TypeFragment.foodTypes);
        freshAddons();
    }

    void updateSelected() {
        int line1Length = 0;
        boolean lineOneFull = false;
        if (!selectedTypes.isEmpty())
            selectedText.setVisibility(View.VISIBLE);
        else selectedText.setVisibility(View.INVISIBLE);

        selectedLayout1.removeAllViews();
        selectedLayout2.removeAllViews();
        for (final String selected : selectedTypes) {
            Button button = new Button(this);
            button.setText(selected);
            button.setTextSize(12);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedTypes.remove(selected);
                    if (currentChecked.containsKey(selected)) {
                        CheckBox checkBox = findViewById(currentChecked.get(selected));
                        checkBox.setChecked(false);
                    }
                    currentChecked.remove(selected);
                    updateSelected();
                }
            });
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(55 * selected.length() + 55, ViewGroup.LayoutParams.MATCH_PARENT);
            if (!lineOneFull) {
                if (line1Length + 55 * selected.length() + 55 < selectedLayout1.getMeasuredWidth()) {
                    line1Length += 55 * selected.length() + 55;
                    selectedLayout1.addView(button, layoutParams);
                } else {
                    selectedLayout2.addView(button, layoutParams);
                    lineOneFull = true;
                }
            } else selectedLayout2.addView(button, layoutParams);

        }
    }

    void freshAddons() {
        currentChecked.clear();
        selectionLayout1.removeAllViews();
        selectionLayout2.removeAllViews();
        Collections.shuffle(types);
        for (int i = 0; i < 10; i++) {
            final AppCompatCheckBox checkBox = new AppCompatCheckBox(this);
            checkBox.setText(types.get(i));
            checkBox.setId(i);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        selectedTypes.add(checkBox.getText().toString());
                        currentChecked.put(checkBox.getText().toString(), checkBox.getId());
                        updateSelected();
                    } else {
                        selectedTypes.remove(checkBox.getText().toString());
                        currentChecked.remove(checkBox.getText().toString());
                        updateSelected();
                    }
                }
            });
            if (i % 2 == 0)
                selectionLayout1.addView(checkBox);
            else selectionLayout2.addView(checkBox);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.adding_tag_fresh:
                Log.d("selectedTypes", selectedTypes.toString());
                freshAddons();
                break;
            case R.id.adding_tag_finish:
                finish();
                new AddingTagThread(handler,selectedTypes).start();
                break;
            case R.id.adding_tag_back:
                finish();
                break;
            case R.id.adding_tag_skip:
                finish();
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(context, "喜好标签添加成功", Toast.LENGTH_SHORT).show();
        }
    };
}
