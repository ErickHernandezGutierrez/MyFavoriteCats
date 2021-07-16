package com.example.myfavoritecats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        CatItem clickedCat = (CatItem) getIntent().getSerializableExtra("CLICKED_CAT");

        ImageView previewImageView = findViewById(R.id.preview_image_view);
        DownloadImageAsyncTask imageDownloader = new DownloadImageAsyncTask( previewImageView );
        imageDownloader.execute( clickedCat.getURL() );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setHomeButtonEnabled(false);      // Disable the button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); // Remove the left caret
        getSupportActionBar().setDisplayShowHomeEnabled(false); // Remove the icon

        return super.onCreateOptionsMenu(menu);
    }
}