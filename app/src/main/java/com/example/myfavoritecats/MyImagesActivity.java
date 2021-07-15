package com.example.myfavoritecats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class MyImagesActivity extends AppCompatActivity implements CatsViewAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private CatsViewAdapter mCatsViewAdapter;
    private ArrayList<CatItem> mSavedCats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_images);

        // get savedCats from the previous activity
        mSavedCats = (ArrayList<CatItem>) getIntent().getSerializableExtra("SAVED_CATS");

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mCatsViewAdapter = new CatsViewAdapter(this, mSavedCats);
        mRecyclerView.setAdapter(mCatsViewAdapter);
        mCatsViewAdapter.setOnItemClickListener( MyImagesActivity.this );
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this, PreviewActivity.class);
        CatItem clickedCat = mSavedCats.get(position);
        intent.putExtra("CLICKED_CAT", clickedCat);
        startActivity(intent);
    }
}