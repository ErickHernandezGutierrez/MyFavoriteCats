package com.example.myfavoritecats;

public class CatItem {
    private String mAPIID;
    private String mURL;
    private int mWidth;
    private int mHeight;

    public CatItem(String catapi_id, String url, int width, int height){
        mAPIID = catapi_id;
        mURL = url;
        mWidth = width;
        mHeight = height;
    }

    public String getAPIID(){ return mAPIID; }
    public String getURL(){ return mURL; }
    public int getWidth(){ return mWidth; }
    public int getmHeight(){ return mHeight; }
}
