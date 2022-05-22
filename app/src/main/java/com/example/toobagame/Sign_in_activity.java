package com.example.toobagame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_in_activity extends AppCompatActivity {
    private EditText ed_Email_signIn, ed_Password_signIn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Button bt_signIn, bt_ExitSignIn;
    private TextView txt_toRecetActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
    }

    private void init(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        ed_Email_signIn = findViewById(R.id.nameEmail_edit_sign_in);
        ed_Password_signIn = findViewById(R.id.password_edit_sign_in);
        bt_signIn = findViewById(R.id.sign_in_button);
        bt_ExitSignIn = findViewById(R.id.bt_logOut_SignIn);
        txt_toRecetActivity = findViewById(R.id.txt_toRecetActivity);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signIn_listener(View view) {//Слушатель аутинфикации
        if(!TextUtils.isEmpty(ed_Email_signIn.getText().toString()) && !TextUtils.isEmpty(ed_Password_signIn.getText().toString())){
            mAuth.signInWithEmailAndPassword(ed_Email_signIn.getText().toString(), ed_Password_signIn.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "User SignIn Successful", Toast.LENGTH_SHORT).show();
                        User user = User.getInstance();
                        user.setEmail(ed_Email_signIn.getText().toString());
                        user.setPassword(ed_Password_signIn.getText().toString());
                        Intent toMasterActivityIntent = new Intent(Sign_in_activity.this, MasterActivity.class);
                        Sign_in_activity.this.startActivity(toMasterActivityIntent);
                        Sign_in_activity.this.finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "User SignIn failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser != null) {
            {
                String userEmail = "You sign in like:" + cUser.getEmail();
            }
            Toast.makeText(this, "User not null", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User null", Toast.LENGTH_SHORT).show();
        }
    }

    public void toMainActivitySignIn(View view) {

        Intent toMainActivityIntent = new Intent(Sign_in_activity.this, MainActivity.class);
        Sign_in_activity.this.startActivity(toMainActivityIntent);
        Sign_in_activity.this.finish();
    }

    public void toRecetActivity(View view) {
        Intent toRecetPasswordActivity = new Intent(Sign_in_activity.this, ActivityChangePassword.class);
        Sign_in_activity.this.startActivity(toRecetPasswordActivity);
        Sign_in_activity.this.finish();
    }
}