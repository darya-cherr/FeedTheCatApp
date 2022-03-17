package com.example.feedthecatapp;

import static android.content.ContentValues.TAG;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int counter;
    private final int clickCount = 15;
    private final int repeatCount = 10;
    private TextView count;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;


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

        count = findViewById(R.id.counter);
        counter = Integer.parseInt(count.getText().toString());

        loadResult();

        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = findViewById(R.id.counter);

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

    private void loadResult(){

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd,MM,yyyy");

        user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid()+"/Results/" + dateFormat.format(currentDate));

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    counter = snapshot.getValue(Integer.class);
                    count.setText(Integer.toString(counter));
                }else{
                    myRef.setValue(counter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    protected void onPause() {
        myRef.setValue(counter);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    @Override
    protected void onDestroy() {

        myRef.setValue(counter);

        super.onDestroy();
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
        count = findViewById(R.id.counter);
        final int id = item.getItemId();
        switch (id){
            case R.id.menu_about_me:
                Intent intent = new Intent(MainActivity.this, AboutMe.class);
                startActivity(intent);
                return;
            case R.id.menu_share:
                shareClick();
                return;
            case R.id.menu_results:
                Intent intent1 = new Intent(MainActivity.this, MyResults.class);
                startActivity(intent1);
                return;
        }
    }

}
