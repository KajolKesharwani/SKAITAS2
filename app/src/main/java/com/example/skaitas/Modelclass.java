package com.example.skaitas;

public class Modelclass {

    private int img;
    private String aitxt,aidesc;

    Modelclass(int img, String aitxt,String aidesc){
        this.img=img;
        this.aitxt=aitxt;
        this.aidesc=aidesc;
    }

    public int getImg() {
        return img;
    }

    public String getAitxt() {
        return aitxt;
    }

    public String getAidesc() {
        return aidesc;
    }
}
