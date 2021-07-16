package com.example.myfavoritecats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;

public class MyImagesActivity extends AppCompatActivity implements CatsViewAdapter.OnItemClickListener {

    private ArrayList<CatItem> mSavedCats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_images);

        // get savedCats from the previous activity
        mSavedCats = (ArrayList<CatItem>) getIntent().getSerializableExtra("SAVED_CATS");

        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        CatsViewAdapter catsViewAdapter;
        catsViewAdapter = new CatsViewAdapter(this, mSavedCats);
        recyclerView.setAdapter(catsViewAdapter);
        catsViewAdapter.setOnItemClickListener( MyImagesActivity.this );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setHomeButtonEnabled(false);      // Disable the button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); // Remove the left caret
        getSupportActionBar().setDisplayShowHomeEnabled(false); // Remove the icon

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(this, PreviewActivity.class);
        CatItem clickedCat = mSavedCats.get(position);
        intent.putExtra("CLICKED_CAT", clickedCat);
        startActivity(intent);
    }
}