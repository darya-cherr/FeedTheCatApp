package com.example.feedthecatapp;

import static android.graphics.Color.rgb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MyResults extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private HashMap<String, Integer> results;
    private String p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_results);

        ImageView back = findViewById(R.id.results_back);

        getDataFromFB();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getDataFromFB(){
        user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("Users/" + user.getUid() + "/Results");

        results = new HashMap<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren()) {
                    results.put(ds.getKey(), ds.getValue(Integer.class));
                }
                addTables();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addTables(){
        TableLayout tableLayout = findViewById(R.id.table);

        for (Map.Entry<String,Integer> result : results.entrySet()) {
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(rgb(246,134,52));

            TextView textViewDate = new TextView(this);
            textViewDate.setText(result.getKey());
            textViewDate.setLayoutParams(findViewById(R.id.date).getLayoutParams());
            textViewDate.setTextSize(20);
            textViewDate.setTextColor(rgb(255,255,255));
            textViewDate.setGravity(Gravity.CENTER);
            tableRow.addView(textViewDate);

            TextView textViewRes = new TextView(this);
            textViewRes.setText(result.getValue().toString());
            textViewRes.setLayoutParams(findViewById(R.id.res).getLayoutParams());
            textViewRes.setTextSize(20);
            textViewRes.setTextColor(rgb(255,255,255));
            textViewRes.setGravity(Gravity.CENTER);
            tableRow.addView(textViewRes);

            tableLayout.addView(tableRow);
        }

    }
}