package com.example.wif;

public class ExampleRecipe {
    private String mLine1;
    private String mLine2;
    private String mLine3;
    private String mImageResource;
    private String mrecipeURL;
    public ExampleRecipe(String imageResource, String line1, String line2,String line3,String recipeURL) {
        mLine1 = line1;
        mLine2 = line2.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\,","\n").replaceAll("\"","").replaceAll(" chopped","").replaceAll(" sliced","");
        mLine3 = line3.replaceAll("\\[","").replaceAll("\\]","").replaceAll("\\,","\n").replaceAll("\"","");
        mImageResource = imageResource;
        mrecipeURL = recipeURL;
    }

    public String getLine1() {
        return mLine1;
    }
    public String getrecipeURL() {
        return mrecipeURL;
    }
    public String getLine2() {
        return mLine2;
    }
    public String getImageResource(){
        return mImageResource;
    }

    public String getLine3() {
        return mLine3;
    }
}
