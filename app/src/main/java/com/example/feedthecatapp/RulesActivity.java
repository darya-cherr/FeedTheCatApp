package com.example.feedthecatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RulesActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout layout;
    SlideAdapter slideAdapter;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        viewPager = findViewById(R.id.pageView);
        layout = findViewById(R.id.dotsLayout);
        backBtn = findViewById(R.id.back_icon);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        slideAdapter = new SlideAdapter(this);
        viewPager.setAdapter(slideAdapter);

    }
}