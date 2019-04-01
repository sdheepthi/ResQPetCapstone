package com.example.loginandregister.AccountActivity;

public class userpets {
    String p_uID, UID, PID, Status, Flag;

    public userpets(String p_uID, String UID, String PID, String status, String flag) {
        this.p_uID = p_uID;
        this.UID = UID;
        this.PID = PID;
        Status = status;
        Flag = flag;
    }

    public String getP_uID() {
        return p_uID;
    }

    public String getUID() {
        return UID;
    }

    public String getPID() {
        return PID;
    }

    public String getStatus() {
        return Status;
    }

    public String getFlag() {
        return Flag;
    }

    public void setP_uID(String p_uID) {
        this.p_uID = p_uID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }
}
