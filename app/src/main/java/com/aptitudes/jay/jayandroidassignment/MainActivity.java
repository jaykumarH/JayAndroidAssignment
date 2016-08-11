package com.aptitudes.jay.jayandroidassignment;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int score = 0;
    private int questionNumber = 0;
    private HashSet<Object> arrayOfQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        readQuestions();
    }

    //region Custom Methods
    public void setupViews() {
        Button option1 = (Button) findViewById(R.id.option1);
        Button option2 = (Button) findViewById(R.id.option2);
        Button option3 = (Button) findViewById(R.id.option3);
        Button option4 = (Button) findViewById(R.id.option4);
        option1.setBackgroundColor(Color.parseColor("#ffbd4a"));
        option2.setBackgroundColor(Color.parseColor("#ffbd4a"));
        option3.setBackgroundColor(Color.parseColor("#ffbd4a"));
        option4.setBackgroundColor(Color.parseColor("#ffbd4a"));
    }

    public void readQuestions() {
        String json = null;
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONArray arrayData = new JSONArray(json);
            arrayOfQuestions = new HashSet<Object>(5);
            Random random = new Random();
            for (int i = 0; i < arrayData.length(); i++) {
                int randomIndex = random.nextInt(arrayData.length());
                arrayOfQuestions.add(arrayData.getJSONObject(i));
            }
//            Random random = new Random();
//            ArrayList<Object> filtered=random.ints(0,arrayData.length()).distinct().limit(5).collect(Collectors.toList());
            Log.d("response", json);
        } catch (Exception e) {
            Log.e("file", "exception is " + e);
        }
    }
    //endregion
}