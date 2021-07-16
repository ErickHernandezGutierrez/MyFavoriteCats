package com.example.myfavoritecats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CatsViewAdapter extends RecyclerView.Adapter<CatsViewAdapter.CatsViewHolder> {
    private final Context mContext;
    private final ArrayList<CatItem> mCatsList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CatsViewAdapter(Context context, ArrayList<CatItem> cats){
        mContext = context;
        mCatsList = cats;
    }

    public class CatsViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;

        public CatsViewHolder(View catView){
            super(catView);
            mImageView = catView.findViewById(R.id.favorite_image_view);

            catView.setOnClickListener(v -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.OnItemClick(position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public CatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
