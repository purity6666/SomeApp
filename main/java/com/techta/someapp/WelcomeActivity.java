package com.techta.someapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private ImageButton proceedBtn;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Animation buttonAnim = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        final Animation loadAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom);

        proceedBtn = findViewById(R.id.imageButton);
        textView = findViewById(R.id.welcomeTextView);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            proceedBtn.setVisibility(View.GONE);
                            textView.setAnimation(loadAnim);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    proceedBtn.setVisibility(View.VISIBLE);
                                    proceedBtn.setAnimation(fromBottom);
                                }
                            }, 2000);
                        }
                    });
                }
            }
        });

        thread.start();

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceedBtn.startAnimation(buttonAnim);
                goToMain();
            }
        });
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        this.finish();
    }
}