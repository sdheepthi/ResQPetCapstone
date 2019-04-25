package com.example.loginandregister.AccountActivity;

public class favouritePet {

    String pid, uid, id, petName, breed, age, gender, pid_user;

    public favouritePet(String id,String uid, String pid, String breed, String petName, String gender, String age) {

        this.id = id;
        this.pid = pid;
        this.uid = uid;
        this.age = age;
        this.breed = breed;
        this.petName = petName;
        this.gender = gender;
        this.pid_user = pid+uid;

    }

    public favouritePet() {

    }

    public String getPid_user() {
        return pid_user;
    }

    public void setPid_user(String pid_user) {
        this.pid_user = pid_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
