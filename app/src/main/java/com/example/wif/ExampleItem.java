package com.example.wif;

public class ExampleItem {
    private String mLine1;
    private String mLine2;
    private int mImageResource;
    public ExampleItem(int imageResource, String line1, String line2) {
        mLine1 = line1;
        mLine2 = line2;
        mImageResource = imageResource;
    }

    public String getLine1() {
        return mLine1;
    }

    public String getLine2() {
        return mLine2;
    }
    public int getImageResource(){
        return mImageResource;
    }
}
