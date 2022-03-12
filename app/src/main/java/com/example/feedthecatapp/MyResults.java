package com.example.feedthecatapp;

import static android.graphics.Color.rgb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MyResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_results);

        ImageView back = findViewById(R.id.results_back);

        addTables();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void addTables(){
        TableLayout tableLayout = findViewById(R.id.table);

        for(int i = 0; i < 25; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(rgb(246,134,52));

            TextView textViewDate = new TextView(this);
            textViewDate.setText("Date");
            textViewDate.setLayoutParams(findViewById(R.id.date).getLayoutParams());
            textViewDate.setTextSize(20);
            textViewDate.setTextColor(rgb(255,255,255));
            textViewDate.setGravity(Gravity.CENTER);
            tableRow.addView(textViewDate);

            TextView textViewRes = new TextView(this);
            textViewRes.setText("Result");
            textViewRes.setLayoutParams(findViewById(R.id.res).getLayoutParams());
            textViewRes.setTextSize(20);
            textViewRes.setTextColor(rgb(255,255,255));
            textViewRes.setGravity(Gravity.CENTER);
            tableRow.addView(textViewRes);

            tableLayout.addView(tableRow);
        }
    }
}