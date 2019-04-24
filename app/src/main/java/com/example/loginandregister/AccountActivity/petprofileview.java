package com.example.loginandregister.AccountActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.loginandregister.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;


public class petprofileview extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  pet_profile_adapter adapter;
    private List<pet> petlist; // was petsearch
    private List<pet> availList;
    private HashSet<String> pid;

    private ProgressDialog progressDialog;

    // database references
    DatabaseReference dbpets;
    DatabaseReference dbuserpets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_recycle);
        FirebaseApp.initializeApp(this);
        Bundle extras = getIntent().getExtras();
        final String Pet_id= extras.getString("petid");
        final String Pet_Name= extras.getString("petName");
        final String pet_age = extras.getString("petAge");
        final String pet_breed = extras.getString("PetBreed");
        final String pet_gender = extras.getString("petGender");
//        final String pet_type = extras.getString("pettype");

        progressDialog = new ProgressDialog(this);

        recyclerView = findViewById(R.id.profile_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        petlist =new ArrayList<>();
        availList = new ArrayList<>();
        pid = new HashSet<String>();
        adapter = new pet_profile_adapter(this, availList);
        recyclerView.setAdapter(adapter);

        dbuserpets = FirebaseDatabase.getInstance().getReference("userpers");
        dbpets = FirebaseDatabase.getInstance().getReference("pets");
        Query findpetId, genpetList;

        findpetId=FirebaseDatabase.getInstance().getReference("pets").orderByChild("petID").equalTo(Pet_id);
        findpetId.addListenerForSingleValueEvent(userpetsListener);


    }


    ValueEventListener userpetsListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            availList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    pet avai = snapshot.getValue(pet.class);
                    availList.add(avai);

                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

        @Override
        protected void onStart() {
            super.onStart();
        }

    }






