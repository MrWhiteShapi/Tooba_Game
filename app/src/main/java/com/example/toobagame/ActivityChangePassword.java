package com.example.toobagame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityChangePassword extends AppCompatActivity {
    Button bt_ResetPassword;
    EditText ed_ResetName, ed_ResetEmail;
    FirebaseAuth auth;
    TextView txt_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    private void init(){
        bt_ResetPassword = findViewById(R.id.bt_resetPassword);
        ed_ResetName = findViewById(R.id.ed_ResetName);
        ed_ResetEmail = findViewById(R.id.ed_ResetEmail);
        txt_info = findViewById(R.id.textView2);
        ed_ResetName.setVisibility(View.INVISIBLE);
        auth = FirebaseAuth.getInstance();
    }

    public void onClickResetPassword(View view) {
        String ed_email = ed_ResetEmail.getText().toString();
        String user_email = auth.getCurrentUser().getEmail();
        String ed_name = ed_ResetName.getText().toString();
        String user_name = auth.getCurrentUser().getDisplayName();
        if(ed_email.equals(user_email))  {
            auth.sendPasswordResetEmail(ed_ResetEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong email or user is not registered ", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
//        else if(ed_ResetEmail.getText().toString().equals(ed_ResetName.getText().toString())){
//            auth.getCurrentUser().updatePassword(ed_ResetEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(getApplicationContext(), "Password changed", Toast.LENGTH_SHORT).show();
//                        Intent toMasterActivityfromActivityChangePassword = new Intent(ActivityChangePassword.this, MasterActivity.class);
//                        ActivityChangePassword.this.startActivity(toMasterActivityfromActivityChangePassword);
//                        ActivityChangePassword.this.finish();
//                    }else {
//                        Log.e("My_App", "Mistake in change password failed");
//                    }
//                }
//            });
//        }
    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//        ed_ResetName.setVisibility(View.VISIBLE);
//        ed_ResetName.setHint("Password");
//        bt_ResetPassword.setText("Изменить пароль");
//        ed_ResetEmail.setHint("Confirm password");
//        txt_info.setText("Введите новый пароль");
//    }
}