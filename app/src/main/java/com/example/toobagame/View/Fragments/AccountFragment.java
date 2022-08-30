package com.example.toobagame.View.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toobagame.Model.Build;
import com.example.toobagame.R;
import com.example.toobagame.Model.User;
import com.example.toobagame.View.Activity.MasterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountFragment extends Fragment implements View.OnClickListener {
    private String email, name, password, age, gender, balance, exp, check_password;
    private String old_password, old_email, old_name, old_age, old_gender;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private FirebaseUser currentUser;
    private User user ;
    private User thisUser ;
    private List<String> genders = new ArrayList<>();
    private EditText ed_NameAccount, ed_EmailAccount, ed_PasswordAccount, ed_AgeAccount;
    private TextView txt_BalanceAccount, txt_RespectAccount, txt_balance, txt_raspect;
    private Spinner spinnerAccount;
    private ConstraintLayout lay_ConstraitEditButton, lay_LogOutConstraiteAccount;
    private ImageView img_Avatar, img_Negative, img_Positive;
    private boolean ARE_ADITING = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        thisUser = User.getInstance();
        user = User.getInstance();
        currentUser = auth.getCurrentUser();
        genders.add(0, "Empty");
        genders.add(1, "Male");
        genders.add(2, "Female");
    }

    private void saveChanges(){//Метод для заполения синглтон класса User для использования во всем приложении
        old_age = (ed_AgeAccount.getText().toString());
        old_email = (ed_EmailAccount.getText().toString());
        old_password = (ed_PasswordAccount.getText().toString());
        old_name = (ed_NameAccount.getText().toString());
        old_gender = (spinnerAccount.getSelectedItem().toString());
    }

    private void changeDataListener() {//Чтение в первый раз
        database.getReference("User").child(user.getPassword()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    user = snapshot.getValue(User.class);
                    //////Код заполняющий значения класса синглтона User
                    thisUser.setBalance(user.getBalance());
                    thisUser.setAge(user.getAge());
                    thisUser.setEmail(user.getEmail());
                    thisUser.setPassword(user.getPassword());
                    thisUser.setName(user.getName());
                    thisUser.setExperience(user.getExperience());
                    //////Код заполняющий поля AccountFragment
                    ed_NameAccount.setText(user.getName());
                    ed_EmailAccount.setText(user.getEmail());
                    ed_PasswordAccount.setText(user.getPassword());
                    ed_AgeAccount.setText(user.getAge());
                    txt_BalanceAccount.setText(Integer.toString(user.getBalance()));
                    txt_RespectAccount.setText(user.getExperience());
                    old_password = ed_PasswordAccount.getText().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Read data failed", Toast.LENGTH_SHORT).show();

            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        changeDataListener();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //Определение всех виджетов фрагмента начало
        img_Negative = (ImageView) view.findViewById(R.id.img_negative);
        img_Positive = (ImageView) view.findViewById(R.id.img_positive);
        img_Avatar = (ImageView) view.findViewById(R.id.img_ChatAvatarFrom);

        ed_NameAccount = (EditText) view.findViewById(R.id.edit_NameAccount);
        ed_EmailAccount = (EditText) view.findViewById(R.id.edit_EmailAccount);
        ed_PasswordAccount = (EditText) view.findViewById(R.id.edit_PasswordAccount);
        ed_AgeAccount = (EditText) view.findViewById(R.id.ed_AgeAccount);

        txt_balance = (TextView) view.findViewById(R.id.txt_Balance);
        txt_raspect = (TextView) view.findViewById(R.id.txxt_Experience);
        txt_BalanceAccount = (TextView) view.findViewById(R.id.txt_BalanceAccount);
        txt_RespectAccount = (TextView) view.findViewById(R.id.txt_Experience);

        spinnerAccount = (Spinner) view.findViewById(R.id.spinnerAccount);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, genders);
        spinnerAccount.setAdapter(adapter);

        lay_ConstraitEditButton = (ConstraintLayout) view.findViewById(R.id.lay_ConstraitEditButton);
        lay_ConstraitEditButton.setOnClickListener(this::onClick);
        lay_LogOutConstraiteAccount = (ConstraintLayout) view.findViewById(R.id.lay_LogOutConstraiteAccount);
        lay_LogOutConstraiteAccount.setOnClickListener(this::onClick);
        //Определение всех виджетов фрагмента конец

        transViewAccount(false);

        return view;
    }

    //Изменить кликабельность view в Account
    private void transViewAccount(boolean flag) {
        ed_NameAccount.setEnabled(flag);
        ed_EmailAccount.setEnabled(flag);
        ed_PasswordAccount.setEnabled(flag);
        ed_AgeAccount.setEnabled(flag);
        spinnerAccount.setEnabled(flag);
    }


    private void onEditLayout() {//Метод включающийся после входа в режим изменения данных
        //Переводит все TextView в режим изменения
        if (ARE_ADITING == false) {
            email = ed_EmailAccount.getText().toString();
            name = ed_NameAccount.getText().toString();
            password = ed_PasswordAccount.getText().toString();
            age = ed_AgeAccount.getText().toString();
            gender = spinnerAccount.getSelectedItem().toString();
            balance = txt_BalanceAccount.getText().toString();
            exp = txt_RespectAccount.getText().toString();

            ed_AgeAccount.setHint(age);
            ed_NameAccount.setHint(name);
            ed_EmailAccount.setHint(email);
            ed_PasswordAccount.setHint(password);

            ARE_ADITING = true;

            transViewAccount(true);
            txt_raspect.setVisibility(View.INVISIBLE);
            txt_balance.setVisibility(View.INVISIBLE);
            txt_RespectAccount.setVisibility(View.INVISIBLE);
            txt_BalanceAccount.setVisibility(View.INVISIBLE);

            img_Negative.setImageResource(R.drawable.ic_clear);
            img_Positive.setImageResource(R.drawable.ic_edit);
        } else if (ARE_ADITING == true) {
            transViewAccount(false);
            ARE_ADITING = false;
            txt_raspect.setVisibility(View.VISIBLE);
            txt_balance.setVisibility(View.VISIBLE);
            txt_RespectAccount.setVisibility(View.VISIBLE);
            txt_BalanceAccount.setVisibility(View.VISIBLE);
            img_Negative.setImageResource(R.drawable.ic_log_out);
            img_Positive.setImageResource(R.drawable.ic_edit_button);
            onFirebaseAuthChange();
            showAlertChangeWithTwoButton();
        }
    }


    private void onRealtimeDatabaseChange() {
        saveChanges();//__________________________________________
        user.setAge(old_age); //                                ||
        user.setEmail(old_email);//                             ||==\ Заполняет синглтон обьект класса User изменнеными данными
        user.setPassword(old_password);//                       ||==/
        user.setName(old_name);//                               ||
        user.setGender(old_gender);//-----------------------------
        database.getReference("User").child(old_password).removeValue();// удаляет старые данные пользователя
        database.getReference("User").child(user.getPassword()).setValue(user);//записывает новые
        onFirebaseAuthChange();
        Toast.makeText(getContext(), "Данные успешно сохранены", Toast.LENGTH_SHORT).show();
    }

    private void onFirebaseAuthChange() {//Если изменяется или пароль или почта
        auth.getCurrentUser().updateEmail(ed_EmailAccount.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                     Log.d("My_App", "Почта изменена");
                }
            }
        });
        auth.getCurrentUser().updatePassword(ed_PasswordAccount.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("My_App", "Парoль изменен");
                }
            }
        });
    }

    private void onLogOutLayout() {
        if (ARE_ADITING == false) {
            System.out.println();
            showAlertWithTwoButton();//Код для всплывающего окна предупреждения
        } else {
            ed_NameAccount.setText(name);
            ed_EmailAccount.setText(email);
            ed_PasswordAccount.setText(password);
            txt_RespectAccount.setVisibility(View.VISIBLE);
            txt_BalanceAccount.setVisibility(View.VISIBLE);
            txt_raspect.setVisibility(View.VISIBLE);
            txt_balance.setVisibility(View.VISIBLE);
            img_Negative.setImageResource(R.drawable.ic_log_out);
            img_Positive.setImageResource(R.drawable.ic_edit_button);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_ConstraitEditButton:
                Toast.makeText(getContext(), "Change profile enabled", Toast.LENGTH_SHORT).show();
                onEditLayout();
                break;
            case R.id.lay_LogOutConstraiteAccount:
                onLogOutLayout();
                break;
        }
    }

    private void showAlertChangeWithTwoButton(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // Указываем Title
        alertDialog.setTitle("Предупреждение");
        // Указываем текст сообщение
        alertDialog.setMessage("Вы уверены что хотите изменить данные?");
        // задаем иконку
        alertDialog.setIcon(R.drawable.icon_tooba);
        alertDialog.setCancelable(false);

        // Обработчик на нажатие ДА
        alertDialog.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onRealtimeDatabaseChange();
            }
        });

        // Обработчик на нажатие НЕТ
        alertDialog.setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Код который выполнится после закрытия окна
                Toast.makeText(getContext(), "Изменение данных - отменено", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        // показываем Alert
        alertDialog.create().show();
    }



    public void showAlertWithTwoButton() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // Указываем Title
        alertDialog.setTitle("Предупреждение");
        // Указываем текст сообщение
        alertDialog.setMessage("Вы уверены что хотите выйти из аккаунта?");
        // задаем иконку
        alertDialog.setIcon(R.drawable.icon_tooba);
        alertDialog.setCancelable(false);

        // Обработчик на нажатие ДА
        alertDialog.setPositiveButton("ДА", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                MasterActivity masterActivity = new MasterActivity();
                masterActivity.onBackMainActivity();
            }
        });

        // Обработчик на нажатие НЕТ
        alertDialog.setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Код который выполнится после закрытия окна
                dialog.cancel();
            }
        });
        // показываем Alert
        alertDialog.create().show();
    }

}