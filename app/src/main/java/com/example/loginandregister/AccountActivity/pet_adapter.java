package com.example.loginandregister.AccountActivity;

import android.net.Uri;
import android.widget.Button;

public class pet_adapter {

    private String pet_name, pet_age, pet_breed;
    private Uri filePath;
    private Button favourite;

    public pet_adapter() {
    }

    public pet_adapter(Button favourite) {
        this.favourite = favourite;
    }

    public pet_adapter(String pet_name, String pet_age, String pet_breed, Uri filePath, Button favourite) {
        this.pet_name = pet_name;
        this.pet_age = pet_age;
        this.pet_breed = pet_breed;
        this.filePath = filePath;
        this.favourite = favourite;
    }

    public String getPet_name() {
        return pet_name;
    }

    public String getPet_age() {
        return pet_age;
    }

    public String getPet_breed() {
        return pet_breed;
    }

    public Uri getFilePath() {
        return filePath;
    }

    public Button getFavourite() {
        return favourite;
    }
}
