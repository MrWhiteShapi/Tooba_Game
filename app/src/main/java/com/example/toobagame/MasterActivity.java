package com.example.toobagame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MasterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_Account, img_ListOwn, img_Map, img_LeadBoard, img_Donat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        //Поставить первым фрагмент с картой
        MapFragment mapFragment = new MapFragment();
        FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
        ft4.replace(R.id.container, mapFragment);
        ft4.commit();
        init();
    }
    private void init(){
        img_Account = findViewById(R.id.img_Account);
        img_Donat = findViewById(R.id.img_Donat);
        img_LeadBoard = findViewById(R.id.img_LeadBoard);
        img_Map = findViewById(R.id.img_Map);
        img_ListOwn = findViewById(R.id.img_listOwn);

        img_Map.setOnClickListener(this::onClick);
        img_Account.setOnClickListener(this::onClick);
        img_LeadBoard.setOnClickListener(this::onClick);
        img_ListOwn.setOnClickListener(this::onClick);
        img_Donat.setOnClickListener(this::onClick);
    }


    public void onBackMainActivity(){
        Intent toBackMainActivity = new Intent(MasterActivity.this, MainActivity.class);
        MasterActivity.this.startActivity(toBackMainActivity);
        MasterActivity.this.finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_Account:
                //Изменение выбранной иконки
                img_ListOwn.setImageResource(R.drawable.ic_list_black);
                img_Donat.setImageResource(R.drawable.ic_credit_card_black);
                img_LeadBoard.setImageResource(R.drawable.ic_lead_board_black);
                img_Map.setImageResource(R.drawable.ic_map_black);
                img_Account.setImageResource(R.drawable.ic_account_green);

                AccountFragment accountFragment = new AccountFragment();
                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.container, accountFragment);
                ft1.commit();
                break;
            case R.id.img_listOwn:
                //Изменение выбранной иконки
                img_ListOwn.setImageResource(R.drawable.ic_list_green);
                img_Donat.setImageResource(R.drawable.ic_credit_card_black);
                img_LeadBoard.setImageResource(R.drawable.ic_lead_board_black);
                img_Map.setImageResource(R.drawable.ic_map_black);
                img_Account.setImageResource(R.drawable.ic_account_black);

                ListOwnFragment listOwnFragment = new ListOwnFragment();
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.replace(R.id.container, listOwnFragment);
                ft2.commit();
                break;
            case R.id.img_LeadBoard:
                //Изменение выбранной иконки
                img_ListOwn.setImageResource(R.drawable.ic_list_black);
                img_Donat.setImageResource(R.drawable.ic_credit_card_black);
                img_LeadBoard.setImageResource(R.drawable.ic_lead_board_green);
                img_Map.setImageResource(R.drawable.ic_map_black);
                img_Account.setImageResource(R.drawable.ic_account_black);

                LeaderBoardFragment leaderBoardFragment = new LeaderBoardFragment();
                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                ft3.replace(R.id.container, leaderBoardFragment);
                ft3.commit();
                break;
            case R.id.img_Map:
                //Изменение выбранной иконки
                img_ListOwn.setImageResource(R.drawable.ic_list_black);
                img_Donat.setImageResource(R.drawable.ic_credit_card_black);
                img_LeadBoard.setImageResource(R.drawable.ic_lead_board_black);
                img_Map.setImageResource(R.drawable.ic_map_green);
                img_Account.setImageResource(R.drawable.ic_account_black);

                MapFragment mapFragment = new MapFragment();
                FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                ft4.replace(R.id.container, mapFragment);
                ft4.commit();
                break;
            case R.id.img_Donat:
                //Изменение выбранной иконки
                img_ListOwn.setImageResource(R.drawable.ic_list_black);
                img_Donat.setImageResource(R.drawable.ic_credit_card_green);
                img_LeadBoard.setImageResource(R.drawable.ic_lead_board_black);
                img_Map.setImageResource(R.drawable.ic_map_black);
                img_Account.setImageResource(R.drawable.ic_account_black);

                DonatFragment donatFragment = new DonatFragment();
                FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
                ft5.replace(R.id.container, donatFragment);
                ft5.commit();
                break;
        }
    }
}