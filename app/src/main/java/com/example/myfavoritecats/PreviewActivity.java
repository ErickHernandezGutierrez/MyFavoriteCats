package com.example.myfavoritecats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

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
}