package com.example.toobagame.View.Fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.toobagame.R;
import com.example.toobagame.View.Activity.MasterActivity;

import java.util.ArrayList;
import java.util.List;


public class ServicesFragment extends Fragment{
    ConstraintLayout layMap, layChat, layShop, layProfile,
            layProperty, layLeaderBoard, layFund, laySetting, layAbout;
    private List observers;
    View viewDash;

    private void init(){
        observers = new ArrayList();

        layMap = viewDash.findViewById(R.id.layDash_Map);
        layChat = viewDash.findViewById(R.id.layDash_Chat);
        layShop = viewDash.findViewById(R.id.layDash_Shop);
        layProfile = viewDash.findViewById(R.id.layDash_Profile);
        layProperty = viewDash.findViewById(R.id.layDash_Property);
        layLeaderBoard = viewDash.findViewById(R.id.layDash_LeaderBoard);
        layFund = viewDash.findViewById(R.id.layDash_Fund);
        laySetting = viewDash.findViewById(R.id.layDash_Settings);
        layAbout = viewDash.findViewById(R.id.layDash_About);

        layMap.setOnClickListener(this::onClick);
        layChat.setOnClickListener(this::onClick);
        layShop.setOnClickListener(this::onClick);
        layProfile.setOnClickListener(this::onClick);
        layProperty.setOnClickListener(this::onClick);
        layLeaderBoard.setOnClickListener(this::onClick);
        layFund.setOnClickListener(this::onClick);
        laySetting.setOnClickListener(this::onClick);
        layAbout.setOnClickListener(this::onClick);
    }

   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewDash = inflater.inflate(R.layout.fragment_services, container, false);
        init();

        return viewDash;
    }

    private void onClick(View view) {

        switch (view.getId()){
            case R.id.layDash_Map:
                MapFragment mapFragment = new MapFragment();
                FragmentTransaction ft4 = getActivity().getSupportFragmentManager().beginTransaction();
                ft4.replace(R.id.container, mapFragment);
                ft4.commit();
                break;
            case R.id.layDash_Chat:
                ChatFragment chatFragment = new ChatFragment();
                FragmentTransaction ft5 = getActivity().getSupportFragmentManager().beginTransaction();
                ft5.replace(R.id.container, chatFragment);
                ft5.commit();
                break;
            case R.id.layDash_Profile:
                AccountFragment accountFragment = new AccountFragment();
                FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.container, accountFragment);
                ft1.commit();
                break;
            case R.id.layDash_Fund:
                FundFragment fundFragment = new FundFragment();
                FragmentTransaction ft55 = getActivity().getSupportFragmentManager().beginTransaction();
                ft55.replace(R.id.container, fundFragment);
                ft55.commit();
                break;
            case R.id.layDash_LeaderBoard:
                LeaderBoardFragment leaderBoardFragment = new LeaderBoardFragment();
                FragmentTransaction ft3 = getActivity().getSupportFragmentManager().beginTransaction();
                ft3.replace(R.id.container, leaderBoardFragment);
                ft3.commit();
                break;
            case R.id.layDash_Property:
                ListOwnFragment listOwnFragment = new ListOwnFragment();
                FragmentTransaction ft2 = getActivity().getSupportFragmentManager().beginTransaction();
                ft2.replace(R.id.container, listOwnFragment);
                ft2.commit();
                break;
            case R.id.layDash_Settings:
                SettingFragment settingFragment = new SettingFragment();
                FragmentTransaction flol = getActivity().getSupportFragmentManager().beginTransaction();
                flol.replace(R.id.container, settingFragment);
                flol.commit();
                break;
            case R.id.layDash_About:
                AboutFragment aboutFragment = new AboutFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, aboutFragment);
                fragmentTransaction.commit();
                break;
            case R.id.layDash_Shop:
                ShopFragment shopFragment = new ShopFragment();
                FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.container, shopFragment);
                fragmentTransaction1.commit();
                break;
        }
    }


}