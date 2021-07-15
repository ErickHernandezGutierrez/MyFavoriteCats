package com.example.myfavoritecats;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.myfavoritecats.DownloadImageAsyncTask;

public class CatsViewAdapter extends RecyclerView.Adapter<CatsViewAdapter.CatsViewHolder> {
    private Context mContext;
    private ArrayList<CatItem> mCatsList;

    public CatsViewAdapter(Context context, ArrayList<CatItem> cats){
        mContext = context;
        mCatsList = cats;
    }

    public class CatsViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;

        public CatsViewHolder(View catView){
            super(catView);
            mImageView = catView.findViewById(R.id.favorite_image_view);
        }
    }

    @NonNull
    @Override
    public CatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cat_item, parent, false);
        return new CatsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CatsViewAdapter.CatsViewHolder holder, int position) {
        CatItem currentItem = mCatsList.get(position);

        String imageURL = currentItem.getURL();
        DownloadImageAsyncTask imageDownloader = new DownloadImageAsyncTask(holder.mImageView);
        imageDownloader.execute(imageURL);
    }

    @Override
    public int getItemCount() {
        return mCatsList.size();
    }
}