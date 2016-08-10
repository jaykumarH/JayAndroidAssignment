package com.aptitudes.jay.jayandroidassignment;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    public void setupViews()
    {
        Button option1=(Button) findViewById(R.id.option1);
        Button option2=(Button) findViewById(R.id.option2);
        Button option3=(Button) findViewById(R.id.option3);
        Button option4=(Button) findViewById(R.id.option4);
        option1.setBackgroundColor(Color.parseColor("#ffbd4a"));
        option2.setBackgroundColor(Color.parseColor("#ffbd4a"));
        option3.setBackgroundColor(Color.parseColor("#ffbd4a"));
        option4.setBackgroundColor(Color.parseColor("#ffbd4a"));
    }
}
