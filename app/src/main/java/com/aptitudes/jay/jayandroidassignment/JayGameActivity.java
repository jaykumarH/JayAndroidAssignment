package com.aptitudes.jay.jayandroidassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class JayGameActivity extends AppCompatActivity implements View.OnClickListener {

    private int jayScore = 0;
    private int jayCurrentQuestion = 0;
    private ArrayList<Object> jayArrayOfQuestions;

    //ui components
    private Button jayBtnOption1;
    private Button jayBtnOption2;
    private Button jayBtnOption3;
    private Button jayBtnOption4;
    private TextView jayTxtViewQuestion;
    private TextView jayTxtViewQuestionTracker;
    private MediaPlayer mp;
    private SharedPreferences pref;
    private AsyncTask<String, Void, Void> execute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jay_activity_game);
        jaySetupViews();
        new JayParseJson().execute(JayConstants.jayJSONFileName);
    }

    //region Async task class- File data fetch
    class JayParseJson extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String fileName = strings[0];
            String json = null;
            try {
                InputStream is = getAssets().open(fileName);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                json = new String(buffer, "UTF-8");

                pref = PreferenceManager.getDefaultSharedPreferences(JayGameActivity.this);
                int totalQ = Integer.parseInt(pref.getString(JayConstants.jayPrefTotalQ, "5"));

                JSONObject jsonRootObject = new JSONObject(json);
                JSONArray jsonArray = jsonRootObject.optJSONArray(JayConstants.getJayKeyQuestionArray);
                HashSet<Object> setOfQuestions;
                setOfQuestions = new HashSet<Object>(totalQ);
                Random random = new Random();
                for (int i = 0; i < jsonArray.length() * 2; i++) {
                    int randomIndex = random.nextInt(jsonArray.length());
                    if (setOfQuestions.size() == totalQ) {
                        break;
                    } else {
                        setOfQuestions.add(jsonArray.getJSONObject(randomIndex));
                    }
                }
                jayArrayOfQuestions = new ArrayList<Object>();
                jayArrayOfQuestions.addAll(setOfQuestions);
                is.close();
            } catch (Exception e) {
                Log.e("file", "exception is " + e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            jayPopulateData(jayCurrentQuestion);
        }

    }
    //endregion

    //region Button Delegate methods
    @Override
    public void onClick(View view) {

        JSONObject object = (JSONObject) jayArrayOfQuestions.get(jayCurrentQuestion);
        Button temp = (Button) view;
        Animation btnAnimation;
        try {
            temp.setBackgroundColor(Color.GREEN);
            btnAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
            String correctAns = object.optString(JayConstants.jayKeyAns);
            if (correctAns.equalsIgnoreCase(temp.getText().toString())) {
                jayScore++;
                mp = MediaPlayer.create(this, R.raw.win);
                mp.start();
            } else {
                Log.d("wrong", "your ans is wrong");
                temp.setBackgroundColor(Color.RED);
                btnAnimation = AnimationUtils.loadAnimation(this, R.anim.spring_animation);
                mp = MediaPlayer.create(this, R.raw.loose);
                mp.start();
            }
            view.startAnimation(btnAnimation);
            jayCurrentQuestion++;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            jayPopulateData(jayCurrentQuestion);
                        }
                    });
                }
            };
            Timer timer = new Timer(true);
            timer.schedule(task, 300);

        } catch (Exception e) {
            Log.e("parser", e.getMessage());
        }
    }
    //endregion

    //region Custom Methods
    public void jaySetupViews() {
        // question label
        jayTxtViewQuestion = (TextView) findViewById(R.id.jayTxtViewQuestion);

        //  get all buttons
        jayBtnOption1 = (Button) findViewById(R.id.jayBtnOption1);
        jayBtnOption2 = (Button) findViewById(R.id.jayBtnOption2);
        jayBtnOption3 = (Button) findViewById(R.id.jayBtnOption3);
        jayBtnOption4 = (Button) findViewById(R.id.jayBtnOption4);

        jaySetButtonDefaultColor();
        jayBtnOption1.setOnClickListener(this);
        jayBtnOption2.setOnClickListener(this);
        jayBtnOption3.setOnClickListener(this);
        jayBtnOption4.setOnClickListener(this);

        //tracker label
        jayTxtViewQuestionTracker = (TextView) findViewById(R.id.jayQuestionTracker);

    }

    public void jayToggleButtonEnable(Boolean enable) {
        if (enable) {
            jayBtnOption1.setEnabled(true);
            jayBtnOption2.setEnabled(true);
            jayBtnOption3.setEnabled(true);
            jayBtnOption4.setEnabled(true);
        } else {
            jayBtnOption1.setEnabled(false);
            jayBtnOption2.setEnabled(false);
            jayBtnOption3.setEnabled(false);
            jayBtnOption4.setEnabled(false);
        }
    }

    public void jayPopulateData(int currentQuestion) {
        if (jayCurrentQuestion == jayArrayOfQuestions.size()) {
            // push to result screen
            jayToggleButtonEnable(false);
            if (mp != null) {
                mp.stop();
                mp.reset();
                mp.release();
            }
            Intent intent = new Intent(JayGameActivity.this, JayResultActivity.class);
            intent.putExtra("score", jayScore);
            startActivity(intent);
        } else {
            jayToggleButtonEnable(true);
            jaySetButtonDefaultColor();

            JSONObject object = (JSONObject) jayArrayOfQuestions.get(currentQuestion);
            try {
                String question = object.getString(JayConstants.jayKeyQuestion);
                String ans = object.getString(JayConstants.jayKeyAns);
                JSONArray arrayOptions = object.optJSONArray(JayConstants.jayKeyOptions);

                // populate the views
                jayTxtViewQuestion.setText(question);
                if (currentQuestion == jayArrayOfQuestions.size() - 1) {
                    jayTxtViewQuestionTracker.setText("Almost there!!");
                } else {
                    jayTxtViewQuestionTracker.setText(String.format("(%d/%d)", currentQuestion + 1, jayArrayOfQuestions.size()));
                }

                Collections.shuffle(Arrays.asList(arrayOptions));
                jayBtnOption1.setText(arrayOptions.getString(0));
                jayBtnOption2.setText(arrayOptions.getString(1));
                jayBtnOption3.setText(arrayOptions.getString(2));
                jayBtnOption4.setText(arrayOptions.getString(3));

            } catch (Exception e) {
                Log.e("parsing", e.getMessage());
            }
        }
    }

    public void jaySetButtonDefaultColor() {
        jayBtnOption1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbButtonBg));
        jayBtnOption2.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbButtonBg));
        jayBtnOption3.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbButtonBg));
        jayBtnOption4.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbButtonBg));
    }
    //endregion
}