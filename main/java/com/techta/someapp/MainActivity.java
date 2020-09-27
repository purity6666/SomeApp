package com.techta.someapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkFirstRun();

    }

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;


        int currentVersionCode = BuildConfig.VERSION_CODE;

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        if (currentVersionCode == savedVersionCode) {
            setContentView(R.layout.activity_main);
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("text", "Welcome");
            startActivity(intent);

            this.finish();

        } else if (currentVersionCode > savedVersionCode) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("text", "Your app has been upgraded");
            startActivity(intent);

            this.finish();

        }

        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }
}