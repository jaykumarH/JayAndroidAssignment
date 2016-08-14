package com.aptitudes.jay.jayandroidassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JayMainActivity extends AppCompatActivity {

    private Button btnPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jay_activity_main);

        btnPlay=(Button) findViewById(R.id.buttonPlayNow);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JayMainActivity.this, JayGameActivity.class);
                startActivity(intent);
            }
        });
    }
}
