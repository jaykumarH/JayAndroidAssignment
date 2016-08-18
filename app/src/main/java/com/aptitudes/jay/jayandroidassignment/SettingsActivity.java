package com.aptitudes.jay.jayandroidassignment;

import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class SettingsActivity extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);

//        final EditTextPreference editText = (EditTextPreference) findPreference("pref_questionTotal");
//        editText.setSummary(editText.getText().toString());
//
//        editText.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object o) {
//                editText.setSummary(String.format("Current total is:%d",editText.getText().toString()));
//
//                return false;
//            }
//        });
    }
}
