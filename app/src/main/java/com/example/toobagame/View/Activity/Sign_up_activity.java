package com.example.toobagame.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toobagame.R;
import com.example.toobagame.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_up_activity extends AppCompatActivity {
    //    private final int SPLASH_DESPLAY_LENGHT = 5000;
    private boolean flagnew;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private String USER_KEY = "User";

    private EditText ed_Password, ed_Email, ed_Name;
    private Button bt_SignUp, bt_Exit;
    public User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }


    public void init(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(USER_KEY);
        mAuth = FirebaseAuth.getInstance();
        ed_Password = findViewById(R.id.password_edit_sign_up);
        ed_Email = findViewById(R.id.email_edit_sign_up);
        ed_Name = findViewById(R.id.name_edit_sign_up);
        bt_SignUp = findViewById(R.id.bt_logIn_SignUp);
        bt_Exit = findViewById(R.id.bt_logOut_signUp);
    }

    public void signUp_listener(View view) {
        flagnew = sendEmailVer();
        String password = ed_Password.getText().toString();
        String email = ed_Email.getText().toString();
        if(!TextUtils.isEmpty(password) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(ed_Name.getText().toString()) ) {
            if(checkNumbers(password) && password.length() >= 8){
                mAuth.createUserWithEmailAndPassword(ed_Email.getText().toString(), ed_Password.getText().toString() ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            onSave();
                            Toast.makeText(getApplicationContext(), "User SignUp Successful", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "User SignUp failed", Toast.LENGTH_SHORT).show();
                            Log.e("My_App", String.valueOf(task.getException()));
                        }
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "The password should not contain numbers and longer than 8 characters", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Please entre Email and Password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkNumbers(String password) {
        if (password.contains("1") || password.contains("2") || password.contains("3") || password.contains("4") ||
                password.contains("5") || password.contains("6") || password.contains("7") || password.contains("8") ||
                password.contains("9") || password.contains("0")){
            return false;
        }else{
            return true;
        }
    }

    private void onSave() {//Заполнение данных по новому зарегестрировавшемуся пользователю по ключу его никнейму
        String id = myRef.getKey();
        String email = ed_Email.getText().toString();
        String name = ed_Name.getText().toString();
        String password = ed_Password.getText().toString();
        if(!TextUtils.isEmpty(ed_Password.getText().toString()) && !TextUtils.isEmpty(ed_Email.getText().toString())
                && !TextUtils.isEmpty(ed_Name.getText().toString())){
            User newUser = new User(id, name, email, password, "empty", "empty", 2000, "empty");
            myRef.child(password).setValue(newUser);
            User user = User.getInstance();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            Intent toMasterActivityIntent = new Intent(Sign_up_activity.this, MasterActivity.class);
            Sign_up_activity.this.startActivity(toMasterActivityIntent);
            Sign_up_activity.this.finish();
        }

    }


    private boolean sendEmailVer(){
        final boolean[] flag = {false};
        FirebaseUser user = mAuth.getCurrentUser();
        Log.w("My_App", user.getUid() + " " + user.getEmail() + " " + user.getDisplayName());
        Log.w("My_App", user.getDisplayName());
        assert user != null;
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Check your Mail to verificate your Email", Toast.LENGTH_SHORT).show();
                    flag[0] = true;
                }else{
                    Toast.makeText(getApplicationContext(), "Check your Mail to verificate your Email", Toast.LENGTH_SHORT).show();
                    flag[0] = false;
                }
            }
        });
        return flag[0];
    }



    public void toMainActivity(View view) {
        Intent toMainActivityIntent = new Intent(Sign_up_activity.this, MainActivity.class);
        Sign_up_activity.this.startActivity(toMainActivityIntent);
        Sign_up_activity.this.finish();
    }
}