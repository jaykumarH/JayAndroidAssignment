package com.aptitudes.jay.jayandroidassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class JayMainActivity extends AppCompatActivity {

    private Button btnPlay;
    private TextView txtViewRules;
    private EditText editTextName;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jay_activity_main);

        editTextName = (EditText) findViewById(R.id.editTextName);
        btnPlay = (Button) findViewById(R.id.buttonPlayNow);
        btnPlay.setVisibility(View.GONE);
        txtViewRules = (TextView) findViewById(R.id.textViewRules);

        pref = getSharedPreferences(JayConstants.prefKeyName, MODE_PRIVATE);

        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    btnPlay.setVisibility(View.VISIBLE);
                    editTextName.setVisibility(View.GONE);

                    String name = editTextName.getText().toString();
                    Editor edit = pref.edit();
                    edit.putString(JayConstants.userNameKey, name);
                    edit.putInt(JayConstants.userScoreKey, -1);
                    edit.commit();

                    txtViewRules.setText(String.format("Thank you %s..your are good to go!!", name));
                }
                return false;
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JayMainActivity.this, JayGameActivity.class);
                startActivity(intent);
            }
        });
    }

}
