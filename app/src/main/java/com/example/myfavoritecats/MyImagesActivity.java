package com.example.myfavoritecats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class MyImagesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RequestQueue mRequestQueue;
    private CatsViewAdapter mCatsViewAdapter;
    private ArrayList<CatItem> mCatsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_images);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager( this, 2 ));

        mCatsList = new ArrayList<>();
        mCatsList.add(new CatItem("c9JL47_hX", "https://cdn2.thecatapi.com/images/c9JL47_hX.png", 2232, 1920));
        mCatsList.add(new CatItem("c9JL47_hX", "https://cdn2.thecatapi.com/images/c9JL47_hX.png", 2232, 1920));
        mCatsList.add(new CatItem("c9JL47_hX", "https://cdn2.thecatapi.com/images/c9JL47_hX.png", 2232, 1920));
        mCatsList.add(new CatItem("c9JL47_hX", "https://cdn2.thecatapi.com/images/c9JL47_hX.png", 2232, 1920));

        mCatsViewAdapter = new CatsViewAdapter( this, mCatsList );
        mRecyclerView.setAdapter(mCatsViewAdapter);
    }
}