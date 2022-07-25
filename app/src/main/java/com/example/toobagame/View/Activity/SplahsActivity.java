package com.example.toobagame.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.toobagame.R;

public class SplahsActivity extends AppCompatActivity {

    private final int SPLASH_DESPLAY_LENGHT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splahs);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplahsActivity.this, MainActivity.class);
                SplahsActivity.this.startActivity(mainIntent);
                SplahsActivity.this.finish();
            }
        }, SPLASH_DESPLAY_LENGHT );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
