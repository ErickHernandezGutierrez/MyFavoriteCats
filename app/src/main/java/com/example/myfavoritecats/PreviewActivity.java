package com.example.myfavoritecats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.widget.ImageView;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        // remove back arrow
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);      // Disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // Remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // Remove the icon
        }

        CatItem clickedCat = (CatItem) getIntent().getSerializableExtra("CLICKED_CAT");

        ImageView previewImageView = findViewById(R.id.preview_image_view);
        DownloadImageAsyncTask imageDownloader = new DownloadImageAsyncTask( previewImageView );
        imageDownloader.execute( clickedCat.getURL() );
    }
}