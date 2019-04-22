package com.example.loginandregister.AccountActivity;

public class pet {

    public String petID, petname, petage, petBreed, petdesc, image, petgender, type, fee;
    public boolean vaccination;
    // this is combined fields for querying

    public String type_age, type_gender, age_gender, tp_ag_ge;

    public pet(String petID, String petname, String petage, String petBreed, String petdesc, String image, String petgender, boolean vaccination, String type, String fee) {
        this.petID = petID;
        this.petname = petname;
        this.petage = petage;
        this.petBreed = petBreed;
        this.petdesc = petdesc;
        this.image = image;
        this.petgender = petgender;
        this.vaccination = vaccination;
        this.fee = fee;
        this.type = type;
            this.type_age = type+petage;
            this.type_gender = type+petgender;
            this.age_gender = petage+petgender;
            this.tp_ag_ge = type+petage+petgender;

        }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public pet() {
    }

    public String getPetID() {
        return petID;
    }

    public String getPetname() {
        return petname;
    }

    public String getPetage() {
        return petage;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public String getPetdesc() {
        return petdesc;
    }

    public String getImage() {
        return image;
    }

    public String getPetgender() {
        return petgender;
    }

    public boolean isVaccination() {
        return vaccination;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public void setPetage(String petage) {
        this.petage = petage;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public void setPetdesc(String petdesc) {
        this.petdesc = petdesc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPetgender(String petgender) {
        this.petgender = petgender;
    }

    public void setPetID(String petID) {
        this.petID = petID;
    }

    public void setVaccination(boolean vaccination) {
        this.vaccination = vaccination;
    }

    public String DownloadURL(String id)
    {
        return "image/"+id;
    }

}
