package com.aptitudes.jay.jayandroidassignment;

import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {

    private EditTextPreference editText;
    private SharedPreferences pref;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);

        pref = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        editText = (EditTextPreference) findPreference(JayConstants.jayPrefTotalQ);
//        editText.setSummary(editText.getText().toString());
        editText.setSummary(String.format("Current total is:%s", editText.getText().toString()));


        editText.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
//                String sample=pref.getString(JayConstants.jayPrefTotalQ,"5");
//                editText.setText(sample);
                return true;
            }
        });
        editText.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {
                if (Integer.parseInt((String) o) > 10 || Integer.parseInt((String) o) < 5) {
                    Toast.makeText(SettingsActivity.this, "Please choose a range between 5 and 10!", Toast.LENGTH_SHORT).show();
                } else {
                    editText.setSummary(String.format("Current total is:%s", o));
                    editText.setText((String) o);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString(JayConstants.jayPrefTotalQ, (String) o);
                    edit.commit();
                }

                return false;
            }
        });
    }
}
