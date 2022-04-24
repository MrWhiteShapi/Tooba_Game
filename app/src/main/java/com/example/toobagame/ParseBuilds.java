package com.example.toobagame;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParseBuilds {

    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private List<Build> builds = new ArrayList<>();




   protected void onReadDatafromData(){
       ValueEventListener valueEventListener = new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Build build = ds.getValue(Build.class);
                    builds.add(build);
                }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       };
       database.getReference("Build").addValueEventListener(valueEventListener);
   }

    public List<Build> getBuilds() {
        return builds;
    }
}
