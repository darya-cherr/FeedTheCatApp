package com.example.feedthecatapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private int counter;
    private final int clickCount = 15;
    private final int repeatCount = 10;
    private TextView count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ImageView menuBar = findViewById(R.id.menu_icon);
        ImageView cat = findViewById(R.id.cat_img);
        ImageView share = findViewById(R.id.share_icon);
        Button feedButton = findViewById(R.id.button);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

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

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareClick();
            }
        });

        menuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    public void shareClick(){
        count = findViewById(R.id.counter);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareRes = "Мой результат в FeedTheCat: " + count.getText().toString() + " очков сытости.";

        shareIntent.putExtra(Intent.EXTRA_TEXT,shareRes);
        startActivity(Intent.createChooser(shareIntent,"SHARE"));
    }



    public void navigationMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_about_me){
            Intent intent = new Intent(MainActivity.this, AboutMe.class);
            startActivity(intent);
        }else{
            if(id == R.id.menu_share)
            shareClick();
        }

    }
}