package com.aptitudes.jay.jayandroidassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class JayResultActivity extends AppCompatActivity {

    private TextView jayTxtViewScore;
    private TextView jayTxtViewMessage;
    private Button jayBtnRetakeQuiz;
    private RatingBar jayRatingBar;
    private MediaPlayer mp;
    private TextView txtViewPrevScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jay_result);
        jaySetupViews();

        jayBtnRetakeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp != null) {
                    mp.stop();
                    mp.reset();
                    mp.release();
                }
                Intent intent = new Intent(JayResultActivity.this, JayGameActivity.class);
                startActivity(intent);
            }
        });

    }

    public void jaySetupViews() {
        jayTxtViewMessage = (TextView) findViewById(R.id.textViewMessage);
        jayTxtViewScore = (TextView) findViewById(R.id.textViewResult);
        jayBtnRetakeQuiz = (Button) findViewById(R.id.buttonRetakeQuiz);
        jayRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtViewPrevScore = (TextView) findViewById(R.id.textViewPrevScore);

        SharedPreferences pref = getSharedPreferences(JayConstants.prefKeyName, MODE_PRIVATE);
        String name = pref.getString(JayConstants.userNameKey, "");
        int savedScore = pref.getInt(JayConstants.userScoreKey, -1);

        if (savedScore >= 0) {
            txtViewPrevScore.setText(String.format("Hey %s your previous Score was: %d", name, savedScore));
        }

        int userScore = getIntent().getExtras().getInt("score");
        jayTxtViewScore.setText(String.format("You scored %d out of %d correct questions", userScore, JayConstants.jayTotalRandomQuestions));
        jayRatingBar.setRating(userScore);

        Editor editor = pref.edit();
        editor.putInt(JayConstants.userScoreKey, userScore);
        editor.commit();

        switch (userScore) {
            case 0:
            case 1:
            case 2:
                jayTxtViewMessage.setText(JayConstants.jayTryAgain);
                jayBtnRetakeQuiz.setText("Retake Quiz");
                mp = MediaPlayer.create(this, R.raw.lowscore);
                mp.start();
                break;

            case 3:
                jayTxtViewMessage.setText(JayConstants.jayGoodJob);
                break;

            case 4:
                jayTxtViewMessage.setText(JayConstants.jayExcellent);
                break;

            case 5:
                jayTxtViewMessage.setText(JayConstants.jayGenius);
                mp = MediaPlayer.create(this, R.raw.excellentscore);
                mp.start();
                break;
            default:
                jayTxtViewMessage.setText("");

        }
    }
}