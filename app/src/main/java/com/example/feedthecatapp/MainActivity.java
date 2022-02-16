package com.example.feedthecatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private int counter;
    private final int clickCount = 15;
    private final int repeatCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView menuBar = findViewById(R.id.menu_icon);
        ImageView cat = findViewById(R.id.cat_img);
        Button feedButton = findViewById(R.id.button);
        TextView count = findViewById(R.id.counter);
        RotateAnimation rotateAnimation = new RotateAnimation(-20, 15, 280, 280);

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = Integer.parseInt(count.getText().toString());
                counter++;
                count.setText(Integer.toString(counter));
                if(counter % clickCount == 0){
                    rotateAnimation.setRepeatMode(Animation.REVERSE);
                    rotateAnimation.setRepeatCount(repeatCount);
                    rotateAnimation.setInterpolator(new LinearInterpolator());
                    rotateAnimation.setDuration(400L);
                    cat.startAnimation(rotateAnimation);
                }
            }
        });

        menuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}