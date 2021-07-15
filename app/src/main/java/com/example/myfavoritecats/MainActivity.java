package com.example.myfavoritecats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import com.example.myfavoritecats.DownloadImageAsyncTask;
import com.example.myfavoritecats.HttpPostAsyncTask;

public class MainActivity extends AppCompatActivity {

    public CatItem currentCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showRandomCat();

        Button nextButton = (Button) findViewById(R.id.next_image_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRandomCat();
            }
        });

        Button saveButton = (Button) findViewById(R.id.save_buttom);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserDatabaseHelper dataBase = new UserDatabaseHelper(MainActivity.this );
                dataBase.addCat("c9JL47_hX", "https://cdn2.thecatapi.com/images/c9JL47_hX.png", 2232, 1920);
            }
        });

        Button myImagesButton = (Button) findViewById(R.id.my_images_button);
        myImagesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UserDatabaseHelper dataBase = new UserDatabaseHelper(MainActivity.this );
                Cursor cursor = dataBase.readAllData();
                ArrayList<CatItem> savedCats = getSavedCatsFromCursor(cursor);

                if(savedCats != null){
                    Intent intent = new Intent(MainActivity.this, MyImagesActivity.class);
                    intent.putExtra("SAVED_CATS", savedCats);
                    startActivity(intent);
                }
            }
        });
    }

    private void showRandomCat(){
        HttpPostAsyncTask task = new HttpPostAsyncTask( MainActivity.this );
        task.execute( "https://api.thecatapi.com/v1/images/search" );
    }

    private ArrayList<CatItem> getSavedCatsFromCursor(Cursor cursor){
        ArrayList<CatItem> savedCats = new ArrayList<>();

        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
                String catapi_id = cursor.getString(1);
                String url       = cursor.getString(2);
                int width        = cursor.getInt(3);
                int height       = cursor.getInt(4);

                CatItem newCat = new CatItem(catapi_id, url, width, height);

                savedCats.add(newCat);
            }
        } else{
            Toast.makeText(getBaseContext(), "No saved cats to show.", Toast.LENGTH_SHORT ).show();
            return null;
        }

        return savedCats;
    }
}