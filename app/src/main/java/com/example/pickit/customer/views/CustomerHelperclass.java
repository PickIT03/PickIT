package com.example.pickit.customer.views;

public class CustomerHelperclass {
    public String dbUserName,dbPass,dbPhoneNo;
    public CustomerHelperclass(){

    }
    public CustomerHelperclass(String dbUserName, String dbPass, String dbPhoneNo) {
        this.dbUserName = dbUserName;
        this.dbPass = dbPass;
        this.dbPhoneNo = dbPhoneNo;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPass() {
        return dbPass;
    }

    public void setDbPass(String dbPass) {
        this.dbPass = dbPass;
    }

    public String getDbPhoneNo() {
        return dbPhoneNo;
    }

    public void setDbPhoneNo(String dbPhoneNo) {
        this.dbPhoneNo = dbPhoneNo;
    }

}
