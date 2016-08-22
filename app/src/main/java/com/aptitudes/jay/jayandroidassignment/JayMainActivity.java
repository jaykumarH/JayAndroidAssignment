package com.aptitudes.jay.jayandroidassignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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
        txtViewRules = (TextView) findViewById(R.id.textViewRules);

        PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        String name = pref.getString(JayConstants.userNameKey, "");
        if (name.length() > 0) {
            txtViewRules.setText(String.format("Good to see you back %s!!!", name.toUpperCase()));
            btnPlay.setVisibility(View.VISIBLE);
            editTextName.setVisibility(View.GONE);
        } else {
            btnPlay.setVisibility(View.GONE);
            editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
                        if (editTextName.getText().toString().length() > 0) {
                            btnPlay.setVisibility(View.VISIBLE);
                            editTextName.setVisibility(View.GONE);

                            String name = editTextName.getText().toString();
                            Editor edit = pref.edit();
                            edit.putString(JayConstants.userNameKey, name);
                            edit.putInt(JayConstants.userScoreKey, -1);
                            edit.commit();

                            txtViewRules.setText(String.format("Thank you %s..your are good to go!!", name));
                        } else {
                            Toast.makeText(JayMainActivity.this, "Enter your name sweety!!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    return false;
                }
            });
        }
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JayMainActivity.this, JayGameActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.jay_settings_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
