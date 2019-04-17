package com.example.loginandregister.AccountActivity;

import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.loginandregister.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class searchpage extends AppCompatActivity {

    // fields
    private Button search;
    private Spinner pet_age;
    private RadioButton dog, cat, male, female;
    private EditText breed;

    private FirebaseAuth auth;


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
                startActivity(new Intent(searchpage.this, Profile.class));
                break;

            case R.id.menuSearch:
                startActivity(new Intent(searchpage.this, searchpage.class));
                break;

            case R.id.menuAddPet:
                startActivity(new Intent(searchpage.this, addpetactivity.class));
                //Toast.makeText(this, "You clicked to add pet", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuDonate:
                Toast.makeText(this, "You clicked to donate", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuFavorites:
                Toast.makeText(this, "You clicked for favorites", Toast.LENGTH_SHORT).show();
                break;



        }
        return true;
    }
    ////////////////////////////

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);



        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("ResQpet");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        male = (RadioButton) findViewById(R.id.btn_male);
        female = (RadioButton) findViewById(R.id.btn_female);
        pet_age = (Spinner) findViewById(R.id.spr_petage);
        search = (Button) findViewById(R.id.btn_search);
        dog = (RadioButton) findViewById(R.id.radio_pettypedog);
        cat = (RadioButton) findViewById(R.id.radio_pettypecat);
        breed = (EditText) findViewById(R.id.txt_petbreed);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResults();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void sendResults()
    {
        String pettype = null;
        String petbreed = null;
        petbreed = breed.getText().toString().trim();
        String petgender = null;
        String petage = null;
        petage = pet_age.getSelectedItem().toString();

        if(male.isChecked())
        {
            petgender = "male";
        }
        else if(female.isChecked())
        {
            petgender = "female";
        }
        if(dog.isChecked())
             pettype = "dog";
        else if(cat.isChecked())
            pettype = "cat";

//

        Intent intent = new Intent(searchpage.this, searchresults.class);
        Bundle bundle = new Bundle();
        bundle.putString("Pettype", pettype);
        bundle.putString("PetBreed", petbreed);
        bundle.putString("Petgender",petgender);
        bundle.putString("petage", petage);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }
}
