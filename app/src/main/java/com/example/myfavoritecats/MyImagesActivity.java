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
    private CatsViewAdapter mCatsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_images);

        // get savedCats from the previous activity
        ArrayList<CatItem> savedCats = (ArrayList<CatItem>) getIntent().getSerializableExtra("SAVED_CATS");

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mCatsViewAdapter = new CatsViewAdapter(this, savedCats);
        mRecyclerView.setAdapter(mCatsViewAdapter);
    }
}