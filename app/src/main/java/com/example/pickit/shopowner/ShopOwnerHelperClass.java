package com.example.pickit.shopowner;

public class ShopOwnerHelperClass {
    public String dbUUID,dbShopName,dbPassword,dbAddress;
    public ShopOwnerHelperClass() {
    }

    public ShopOwnerHelperClass(String dbUUID, String dbShopName, String dbPassword , String dbAddress) {
        this.dbUUID = dbUUID;
        this.dbShopName = dbShopName;
        this.dbPassword = dbPassword;
        this.dbAddress = dbAddress;
    }

    public String getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(String dbAddress) {
        this.dbAddress = dbAddress;
    }

    public String getDbUUID() {
        return dbUUID;
    }

    public void setDbUUID(String dbUUID) {
        this.dbUUID = dbUUID;
    }

    public String getDbShopName() {
        return dbShopName;
    }

    public void setDbShopName(String dbShopName) {
        this.dbShopName = dbShopName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPass(String dbPassword) {
        this.dbPassword = dbPassword;
    }
}
