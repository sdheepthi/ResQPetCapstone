package com.example.loginandregister.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.loginandregister.R;

public class Profile extends AppCompatActivity {

    private Button addPet, donate, searchPet;

//NAV BAR/////////////////
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
                startActivity(new Intent(Profile.this, Profile.class));
                break;

            case R.id.menuSearch:
                Toast.makeText(this, "You clicked to search", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuAddPet:
                Toast.makeText(this, "You clicked to add pet", Toast.LENGTH_SHORT).show();
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
////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setting the title
        toolbar.setTitle("ResQpet");

        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);

        addPet = (Button) findViewById(R.id.btn_petadd);
        donate = (Button) findViewById(R.id.btn_donation);
        searchPet = (Button) findViewById(R.id.btn_petsearch);

        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, addpetactivity.class));
            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, PaymentActivity.class));
            }
        });


    }
}


