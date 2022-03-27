package com.example.toobagame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toSignUp(View view) {
        Intent signUp = new Intent(MainActivity.this, Sign_up_activity.class);
        MainActivity.this.startActivity(signUp);
        MainActivity.this.finish();
    }

    public void toSignIn(View view) {
        Intent signIn = new Intent(MainActivity.this, Sign_in_activity.class);
        MainActivity.this.startActivity(signIn);
        MainActivity.this.finish();
    }
}