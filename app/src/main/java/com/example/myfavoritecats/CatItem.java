package com.example.myfavoritecats;

import java.io.Serializable;

public class CatItem implements Serializable {
    private final String mAPIID;
    private final String mURL;
    private final int mWidth;
    private final int mHeight;

    public CatItem(String catapi_id, String url, int width, int height){
        mAPIID  = catapi_id;
        mURL    = url;
        mWidth  = width;
        mHeight = height;
    }

    public String getAPIID(){ return mAPIID; }
    public String getURL(){ return mURL; }
    public int getWidth(){ return mWidth; }
    public int getHeight(){ return mHeight; }
}
