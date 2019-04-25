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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class searchresults extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  petAdapter adapter;
    private List<pet> petlist; // was petsearch
    private List<userpets> availList;
    private Button view;
    private  Button fav;
    private HashSet<String> pid;

    private ProgressDialog progressDialog;

    // database references
    DatabaseReference dbpets;
    DatabaseReference dbuserpets;

    //Nav bar menu/////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.menuHome:
                startActivity(new Intent(searchresults.this, Profile.class));
                break;

            case R.id.menuSearch:
                startActivity(new Intent(searchresults.this, searchpage.class));
                break;

            case R.id.menuAddPet:
                startActivity(new Intent(searchresults.this, addpetactivity.class));
                break;

            case R.id.menuFavorites:
                Toast.makeText(this, "You clicked for favorites", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
    ////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresults);


        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("ResQpet");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);


        FirebaseApp.initializeApp(this);
        Bundle extras = getIntent().getExtras();
        final String pet_type = extras.getString("Pettype");
        final String pet_age = extras.getString("petage");
        final String pet_breed = extras.getString("PetBreed");
        final String pet_gender = extras.getString("gender");
//        final String pet_type = extras.getString("pettype");

        view = (Button) findViewById(R.id.rec_btnView);
        fav = (Button) findViewById(R.id.rec_btnfav);


        recyclerView = findViewById(R.id.search_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        progressDialog = new ProgressDialog(this);


        petlist =new ArrayList<>();
        availList = new ArrayList<>();
        pid = new HashSet<String>();
        adapter = new petAdapter(this, petlist);
        recyclerView.setAdapter(adapter);





        dbuserpets = FirebaseDatabase.getInstance().getReference("userpers");
        dbpets = FirebaseDatabase.getInstance().getReference("pets");
        Query findpetId, genpetList;

        if(!pet_type.isEmpty() && !pet_age.isEmpty() && !pet_gender.isEmpty())
        {
            genpetList = FirebaseDatabase.getInstance().getReference("pets").orderByChild("tp_ag_ge").equalTo(pet_type+pet_age+pet_gender);
            genpetList.addListenerForSingleValueEvent(petlistListner);
        }
        else if(!pet_type.isEmpty() && !pet_age.isEmpty() && pet_gender.isEmpty())
        {
            genpetList = FirebaseDatabase.getInstance().getReference("pets").orderByChild("type_age").equalTo(pet_type+pet_age);
            genpetList.addListenerForSingleValueEvent(petlistListner);
        }
        else if(!pet_type.isEmpty() && pet_age.isEmpty() && !pet_gender.isEmpty())
        {
            genpetList = FirebaseDatabase.getInstance().getReference("pets").orderByChild("type_gender").equalTo(pet_type+pet_gender);
            genpetList.addListenerForSingleValueEvent(petlistListner);
        }
        else if(pet_type.isEmpty() && !pet_age.isEmpty() && !pet_gender.isEmpty())
        {
            genpetList = FirebaseDatabase.getInstance().getReference("pets").orderByChild("age_gender").equalTo(pet_age+pet_gender);
            genpetList.addListenerForSingleValueEvent(petlistListner);
        }
        else if(!pet_type.isEmpty() && pet_age.isEmpty() && pet_gender.isEmpty())
        {
            genpetList = FirebaseDatabase.getInstance().getReference("pets").orderByChild("type").equalTo(pet_type);
            genpetList.addListenerForSingleValueEvent(petlistListner);
        }
        else if(pet_type.isEmpty() && !pet_age.isEmpty() && pet_gender.isEmpty())
        {
            genpetList = FirebaseDatabase.getInstance().getReference("pets").orderByChild("petage").equalTo(pet_age);
            genpetList.addListenerForSingleValueEvent(petlistListner);
        }
        else if(pet_type.isEmpty() && pet_age.isEmpty() && !pet_gender.isEmpty())
        {
            genpetList = FirebaseDatabase.getInstance().getReference("pets").orderByChild("petgender").equalTo(pet_gender);
            genpetList.addListenerForSingleValueEvent(petlistListner);
        }
        else
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("pets");
            ref.addListenerForSingleValueEvent(petlistListner);
        }

//        genpetList = FirebaseDatabase.getInstance().getReference("pets").orderByChild("petgender").equalTo("male").orderByChild("petage").equalTo("0-1 years");
//        genpetList.addListenerForSingleValueEvent(petlistListner);



    }

    ValueEventListener userpetsListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            availList.clear();
            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren() ) {
                    userpets avai = snapshot.getValue(userpets.class);
                    availList.add(avai);

                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



    ValueEventListener petlistListner = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            petlist.clear();
            if(dataSnapshot.exists()) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                   pet pets = snapshot.getValue(pet.class) ; //was petsearch
                   petlist.add(pets);
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
