package com.example.foodrecommend.Entities;

public class Food {
    public String fid;
    public String fName;
    public String picUrl;
    public String fType;

    public Food(String fid, String fName, String picUrl) {
        this.fid = fid;
        this.fName = fName;
        this.picUrl = picUrl;
    }
    public Food(String fid, String fName, String picUrl,String fType) {
        this.fid = fid;
        this.fName = fName;
        this.picUrl = picUrl;
        this.fType = fType;
    }

}
