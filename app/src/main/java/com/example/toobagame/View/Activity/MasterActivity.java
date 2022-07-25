package com.example.toobagame.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.toobagame.R;
import com.example.toobagame.View.Fragments.AccountFragment;
import com.example.toobagame.View.Fragments.DonatFragment;
import com.example.toobagame.View.Fragments.ListOwnFragment;
import com.example.toobagame.View.Fragments.MapFragment;
import com.example.toobagame.View.Fragments.ServicesFragment;
import com.google.android.gms.common.api.internal.ListenerHolder;

public class MasterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_Account, img_ListOwn, img_Map, img_DashBoard, img_Donat;
    private final String MAPKIT_API_KEY = "69d278ed-05cb-4b84-8f4e-777dadafe483";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        init();

        //Поставить первым фрагмент с картой
        img_Account.setImageResource(R.drawable.ic_account_green);
        AccountFragment accountFragment = new AccountFragment();
        FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
        ft4.replace(R.id.container, accountFragment);
//        ft4.replace(R.id.container, mapFragment);
        ft4.commit();
    }

    private void init() {

        img_Account = findViewById(R.id.img_Account);
        img_Donat = findViewById(R.id.img_Donat);
        img_DashBoard = findViewById(R.id.img_DashBoard);
        img_Map = findViewById(R.id.img_Map);
        img_ListOwn = findViewById(R.id.img_listOwn);

        img_Map.setOnClickListener(this::onClick);
        img_Account.setOnClickListener(this::onClick);
        img_DashBoard.setOnClickListener(this::onClick);
        img_ListOwn.setOnClickListener(this::onClick);
        img_Donat.setOnClickListener(this::onClick);
    }


    public void onBackMainActivity() {
        Intent toBackMainActivity = new Intent(MasterActivity.this, MainActivity.class);
        MasterActivity.this.startActivity(toBackMainActivity);
        MasterActivity.this.finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_Account:
                //Изменение выбранной иконки
                img_ListOwn.setImageResource(R.drawable.ic_list_black);
                img_Donat.setImageResource(R.drawable.ic_credit_card_black);
                img_DashBoard.setImageResource(R.drawable.ic_dashboard);
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
                img_DashBoard.setImageResource(R.drawable.ic_dashboard);
                img_Map.setImageResource(R.drawable.ic_map_black);
                img_Account.setImageResource(R.drawable.ic_account_black);

                ListOwnFragment listOwnFragment = new ListOwnFragment();
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.replace(R.id.container, listOwnFragment);
                ft2.commit();
                break;
            case R.id.img_DashBoard:
                //Изменение выбранной иконки
                img_ListOwn.setImageResource(R.drawable.ic_list_black);
                img_Donat.setImageResource(R.drawable.ic_credit_card_black);
                img_DashBoard.setImageResource(R.drawable.ic_dashboard_green);
                img_Map.setImageResource(R.drawable.ic_map_black);
                img_Account.setImageResource(R.drawable.ic_account_black);

                ServicesFragment servicesFragment = new ServicesFragment();
                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                ft3.replace(R.id.container, servicesFragment);
                ft3.commit();
                break;
            case R.id.img_Map:
                //Изменение выбранной иконки
                img_ListOwn.setImageResource(R.drawable.ic_list_black);
                img_Donat.setImageResource(R.drawable.ic_credit_card_black);
                img_DashBoard.setImageResource(R.drawable.ic_dashboard);
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
                img_DashBoard.setImageResource(R.drawable.ic_dashboard);
                img_Map.setImageResource(R.drawable.ic_map_black);
                img_Account.setImageResource(R.drawable.ic_account_black);

                DonatFragment donatFragment = new DonatFragment();
                FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
                ft5.replace(R.id.container, donatFragment);
                ft5.commit();
                break;
        }
    }

    public void getNotify(){
        img_DashBoard.setImageResource(R.drawable.ic_dashboard);
    }

}