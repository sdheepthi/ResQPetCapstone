package com.example.loginandregister.AccountActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.loginandregister.R;

public class Profile extends AppCompatActivity {

    private Button addPet, donate, searchPet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        addPet = (Button) findViewById(R.id.btn_petadd);
        donate = (Button) findViewById(R.id.btn_donation);
        searchPet = (Button) findViewById(R.id.btn_petsearch);

        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, addpetactivity.class));
            }
        });


    }
}
