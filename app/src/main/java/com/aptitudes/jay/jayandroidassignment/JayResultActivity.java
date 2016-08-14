package com.aptitudes.jay.jayandroidassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class JayResultActivity extends AppCompatActivity {

    private TextView jayTxtViewScore;
    private TextView jayTxtViewMessage;
    private Button jayBtnRetakeQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jay_result);
        jaySetupViews();

    }

    public void jaySetupViews() {
        jayTxtViewMessage = (TextView) findViewById(R.id.textViewResult);
        jayTxtViewScore = (TextView) findViewById(R.id.textViewResult);
        jayBtnRetakeQuiz = (Button) findViewById(R.id.buttonRetakeQuiz);

        int userScore = getIntent().getExtras().getInt("score");
        jayTxtViewScore.setText(String.format("You scored %d out of %d correct questions", userScore, 5));
    }

}
